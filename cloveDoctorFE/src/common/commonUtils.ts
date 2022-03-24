export default class CommonUtils {
    static dateFormat(time: Date): string {
        let date: string;
        let year: string = time.getFullYear().toString();
        let trueMon: number = time.getMonth() + 1;
        let mon: string = trueMon + 1 < 10 ? '0' + trueMon.toString() : trueMon.toString();
        let day: string = time.getDate() < 10 ? '0' + time.getDate().toString() : time.getDate().toString();
        let hours: string = time.getHours() < 10 ? '0' + time.getHours().toString() : time.getHours().toString();
        let minutes: string = time.getMinutes() < 10 ? '0' + time.getMinutes().toString() : time.getMinutes().toString();
        let seconds: string = time.getSeconds() < 10 ? '0' + time.getSeconds().toString() : time.getSeconds().toString();
        date = year + '-' + mon + '-' + day + ' ' + hours + ':' + minutes + ':' + seconds;
        return date;
    }
}