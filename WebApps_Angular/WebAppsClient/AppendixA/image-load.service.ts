import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root'
})
export class ImageLoadService {

    private maxImageSize: number = 150;

    constructor() { }

    onDropImageFile(file: File): Promise<string> {
        return this.readFile(file)
            .then(event => this.onFileLoad(event))
            .then(image => this.onImageLoad(image, file.type));
    }

    readFile(file: File): Promise<ProgressEvent> {
        return new Promise((resolve, reject) => {
            const reader = new FileReader();
            reader.onload = e => resolve(e);
            reader.readAsDataURL(file);
        });
    }

    /* When a file is loaded, treat the file contents as an image
     * and resize it. It should really check it is an image!
     */
    onFileLoad(event: ProgressEvent): Promise<HTMLImageElement> {
        const fr: FileReader = event.target as FileReader;
        return new Promise((resolve, reject) => {
            const image: HTMLImageElement = new Image();
            image.onload = () => resolve(image);
            image.src = fr.result as string;
        });
    }

    /* Resize an image to the maximum size. The size is set slightly
     * larger than the display size. The image data is converted to a
     * data URL (it contains the data rather than a reference).
     */
    onImageLoad(image: HTMLImageElement, fileType: string): Promise<string> {
        return new Promise((resolve, reject) => {
            let height = image.height;
            let width = image.width;
            if (height > this.maxImageSize) {
                width = this.scale(height, this.maxImageSize, width);
                height = this.maxImageSize;
            }
            if (width > this.maxImageSize) {
                height = this.scale(width, this.maxImageSize, height);
                width = this.maxImageSize;
            }
            const canvas = document.createElement('canvas');
            const ctx = canvas.getContext('2d');
            canvas.width = width;
            canvas.height = height;
            ctx.drawImage(image, 0, 0, width, height);
            resolve(canvas.toDataURL(fileType));
        })
    }

    /* Scale an independent dimension in the same proportion as a
     * reference dimension
     */
    scale(oldRef: number, newRef: number, oldInd: number): number {
        return (oldInd * newRef) / oldRef;
    }


}
