 let block = document.getElementsByClassName('table-download-btn')
 console.log('Block is', block[0]);

 function tableToCSV() {

            // Variable to store the final csv data
            let csv_data = [];

            // Get each row data
            let rows = document.getElementsByTagName('tr');
            for (let i = 0; i < rows.length; i++) {

                // Get each column data
                let cols = rows[i].querySelectorAll('td,th');

                // Stores each csv row data
                let csvrow = [];
                for (let j = 0; j < cols.length; j++) {

                    // Get the text data of each cell
                    // of a row and push it to csvrow
                    csvrow.push(cols[j].innerHTML);
                }

                // Combine each column value with comma
                csv_data.push(csvrow.join(","));
            }

            // Combine each row data with new line character
            csv_data = csv_data.join('\n');

            console.log(csv_data);

            // Call this function to download csv file  
           downloadCSVFile(csv_data);

        }

        function downloadCSVFile(csv_data) {
            
            // Create CSV file object and feed
            // our csv_data into it
            CSVFile = new Blob([csv_data], {
                encoding:"windows-1251",type:"text/csv;charset=windows-1251"
                
            });

            // Create to temporary link to initiate
            // download process
            let temp_link = document.createElement('a');

            // Download csv file
            temp_link.download = "tb1.csv";
            let url = window.URL.createObjectURL(CSVFile);
            temp_link.href = url;

            // This link should not be displayed
            temp_link.style.display = "none";
            document.body.appendChild(temp_link);

            // Automatically click the link to
            // trigger download
            //window. open (temp_link, target)
            temp_link.click();
            document.body.removeChild(temp_link);
        }

block[0].addEventListener("click", function() {
    tableToCSV();
})





