//INITIAL VALUES
let direction = 0;

function Init(){
let tableHead = document.querySelector('thead');
console.log(tableHead);

let headers = tableHead.getElementsByClassName('td-table');
console.log(headers[0]);

let tableBody = document.querySelector('tbody');

console.log(tableBody);

let tableRows = tableBody.getElementsByClassName('tr-table');

//Name extraction test
let name1 =  tableRows[0].getElementsByClassName("lecturer_name")[0];
console.log('YOLO 420 BL4Z3 IT', (name1.textContent))
//successfull

for(let i = 0; i< headers.length; i++){
    headers[i].addEventListener('click', function(e){
        e.preventDefault();
        let res = sortOut(i, tableRows);
        console.log('RES is ',res);
        console.log(typeof res[0]);
        tableBody.remove();
        let table = document.getElementsByClassName('columns')[0];
        for(let j = 0; j < res.length; j++){
            tableBody.append(res[j])
        }
        table.append(tableBody);
        tableRows = res;
            
    })
}

}

function sortOut(i, tableRows){
    let result;
    let sortBy;
    switch (i){
        case 0:
            sortBy = "lecturer_name";//Сортировка по Имени науч.рука
            result = teachersSort(tableRows, sortBy);
            return result;
        case 1:
            sortBy = "academic_degree";//Сортировка по Научной Степени науч.рука
            result = teachersSort(tableRows, sortBy);
            return result;
        case 2:
            sortBy = "lecturer_position";//Сортировка по Должности науч.рука
            result = teachersSort(tableRows, sortBy);
            return result;
        case 3:
            sortBy = "department_name";//Сортировка по Кафедре науч.рука
            result = teachersSort(tableRows, sortBy);
            return result;
        case 4:
            sortBy = "student_name";//Сортировка по Имени Студента науч.рука
            result = teachersSort(tableRows, sortBy);
            return result;
        case 5:
            sortBy = "stud_num";//Сортировка по Студ.Билету
            result = teachersSort(tableRows, sortBy);
            return result;
        case 6:
            sortBy = "student_citizenship";//Сортировка по Гражданству Студента
            result = teachersSort(tableRows, sortBy);
            return result;
        case 7:
            sortBy = "fqw_name";//Сортировка по Теме
            result = teachersSort(tableRows, sortBy);
            return result;
    default://ЕСЛИ ЧТО-ТО ПРОИЗОЙДЕТ НЕВОЗМОЖНОЕ
        alert('Some woodoo magic ');

    }
}
// Сортировка учителей по заданному парамутру
function teachersSort(tableRows, sortBy){
        tableRows = [...tableRows];//Копирование таблицы, чтобы не портить изначальные значения
        if (tableRows <= 1) {// При Количестве строк <= 1 возвращаем таблицу
            return tableRows;
        }
        // Сортировка
        for (let i = 0; i < tableRows.length; i++) {
            for (let j = 0; j < tableRows.length - 1 - i; j++) { 
            if(direction %2 == 0){
                if (tableRows[j].getElementsByClassName(sortBy)[0].textContent < tableRows[j+1].getElementsByClassName(sortBy)[0].textContent) {
                    //alert('True');
                    let saveVal = tableRows[j];
                    tableRows[j] = tableRows[j+1];
                    tableRows[j+1] = saveVal;
                }
            }
            else if(direction %2 != 0){
                if (tableRows[j].getElementsByClassName(sortBy)[0].textContent > tableRows[j+1].getElementsByClassName(sortBy)[0].textContent) {
                    //alert('True');
                    let saveVal = tableRows[j];
                    tableRows[j] = tableRows[j+1];
                    tableRows[j+1] = saveVal;
                }
            }  
        }
    }
        direction+=1;  
        return tableRows;
}


Init();