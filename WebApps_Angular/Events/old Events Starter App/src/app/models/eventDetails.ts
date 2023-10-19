export class eventDetails {
    constructor(
    public id: number,
    public title: string, 
    public description: string,
    public location: string,
    public sortDate: string,
    public eventDate: string,
    public likes: number,
    public dislikes: number
    ) {}
}