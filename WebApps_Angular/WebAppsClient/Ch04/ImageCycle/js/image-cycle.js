var banner = ['600x399-1.jpg', '600x399-2.jpg', 'gregrutherford.jpg', 'henrymoore.jpg',
    'mushrooms.jpg', 'pasha.jpg', 'peppers.jpg', 'stonehenge.jpg'];

var bannerDestination = ['Algodones_Dunes', 'Antelope_Canyon', 'Greg_Rutherford', 'Henry_Moore',
    'Mushroom', 'Tabby_cat', 'Chili_pepper', 'Stonehenge'];

var imageno = 0;

function cycleBanner() {
    if (++imageno == banner.length) {
        imageno = 0;
    }
    document.bannerImage.src = 'images/' + banner[imageno];
    // change to next image every 3 seconds
    setTimeout('cycleBanner()', 3000);
}

function getImageDestination() {
    document.location.href = 'https://en.wikipedia.org/wiki/' + bannerDestination[imageno];
}