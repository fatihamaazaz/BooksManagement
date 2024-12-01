export interface Book {
    id: string;
    title: string;
    subtitle: string;
    authors: string[];
    description: string;
    imageLink: string;
    pageCount: Number;
    language: string;
    link: string;
    categories: string[];
    publishedDate: Date;
    publisher: string
}