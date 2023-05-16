export const dateFormat = function (date) {
    const mon = date.getMonth()+1;
    let month =  mon>= 10 ? `${mon}` : '0' + mon;

    let day = date.getDate() >= 10 ? `${date.getDate()}` : '0' + date.getDate();
    return date.getFullYear() + '-' + month + '-' + day;
}
