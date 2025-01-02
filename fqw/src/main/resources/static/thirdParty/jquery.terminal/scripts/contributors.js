#!/usr/bin/env node

import https from 'https';
import path from 'path';
import fs from 'fs';
import fetch from 'node-fetch';
import lily from '@jcubic/lily';

const argv = lily(process.argv.slice(2));

function split_equal(array, length) {
    const result = [];
    const len = array.length;
    if (len < length) {
        return [array];
    } else if (length < 0) {
        throw new Error("split_equal: length can't be negative");
    }
    for (let i = 0; i < len; i += length) {
        result.push(array.slice(i, i + length));
    }
    return result;
}

function get(url, query) {
    const options = {
        headers: {
            'User-Agent': 'Node.js'
        }
    };

    if (argv.auth) {
        const [user, pass] = argv.auth.split(':');
        options.headers.Authorization = `Basic ${Buffer.from(`${user}:${pass}`).toString('base64')}`;
    }

    const queryString = new URLSearchParams(query).toString();

    if (queryString) {
        url = `${url}?${queryString}`;
    }

    return fetch(url, options).then(response => {
        if (response.ok) {
            return response.json();
        } else if (response.headers.get('x-ratelimit-remaining') === '0') {
            const date = new Date(parseInt(response.headers.get('x-ratelimit-reset')) * 1000);
            throw new Error('Rate limit util ' + date);
        } else {
            throw new Error('Error code ' + response.status);
        }
    });
}

function get_file(filename) {
    return new Promise(function(resolve, reject) {
        fs.readFile(filename, function(err, data) {
            if (err) {
                reject(err);
            } else {
                resolve(JSON.parse(data.toString()));
            }
        });
    });
}

function get_api(argv) {
    const user = argv.u;
    const repo = argv.r;
    const path = '/repos/' + user + '/' + repo + '/contributors';
    const query = {
        "per_page": 100
    };
    if (argv.t) {
        query['access_token'] = argv.t;
    }
    return get('https://api.github.com' + path, query).then(function(contributors) {
        return Promise.all(contributors.map(function(contributor) {
            const path = contributor.url.replace(/https:\/\/[^\/]+/, '');
            return get('https://api.github.com' + path, query).then(function(user) {
                if (user.name || user.login) {
                    const object = {
                        name: user.name || user.login
                    };
                    if (user.email) {
                        object.email = user.email;
                    }
                    if (user.blog) {
                        object.url = user.blog;
                    } else {
                        object.url = "https://github.com/" + user.login
                    }
                    object.avatar = contributor.avatar_url;
                    object.login = user.login;
                    return object;
                }
            });
        }).filter(Boolean));
    });
}


if (((argv.f && argv.m) || (argv.u && argv.r)) && argv.r) {
    (argv.f ? get_file(argv.f) : get_api(argv)).then(function(contributors) {
        if (argv.m) {
            const split = split_equal(contributors, 7);
            const align = new Array(split[0].length + 1).join('| :---: ') + ' |';
            const rows = split.map(function(list) {
                return '| ' + list.map(function(contributor) {
                    return '[<img src="' + contributor.avatar + '" width="100px;"/>' +
                        '<br /><sub>' + contributor.name + '</sub>](' +
                        contributor.url + ')<br>[commits](https://github.com/jcubic' +
                        '/' + argv.r + '/commits?author=' + contributor.login + ')';
                }).join(' | ') + ' |';
            });
            rows.splice(1, 0, align);
            console.log(rows.join('\n'));
        } else {
            console.log(JSON.stringify(contributors, null, 2));
        }
    }).catch(function(error) {
        console.log('ERROR: ' + error);
    });
} else {
    const script = path.basename(process.argv[1]);
    console.log('usage: \n' + script + '-u <user> -r <repo> ' +
                '[--auth githubUsername:githubPassword] [-m]\n' +
                script + ' -f <json filename> -m');
}
