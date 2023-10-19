import { createWriteStream } from 'fs';
import { ElementFinder, browser } from 'protractor';

export class TestUtils {
    private static writeScreenShot(filename: string, data: string) {
        let stream = createWriteStream(filename);
        stream.write(Buffer.from(data, 'base64'));
        stream.end();
    }

    private static writeSourceFile(filename: string, data: string, ) {
        let stream = createWriteStream(filename);
        stream.write(data);
        stream.end();
    }

    static takeScreenShot(filename: string, el: ElementFinder | null) {
        let context = el || browser;
        context.takeScreenshot()
            .then(png => this.writeScreenShot(filename, png));
    }

    static savePageSource(filename: string) {
        browser.getPageSource()
            .then(source => this.writeSourceFile(filename, source));
    }
}