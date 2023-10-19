
const IMAGE_BASE = 'images/';
const WIKI_BASE = 'https://en.wikipedia.org/wiki/';

const banner = [{
    image: '600x399-1.jpg',
    href: 'Algodones_Dunes',
    desc: 'The Algodones Dunes is a large erg located in the southeastern portion of the U.S. state of California'
}, {
    image: '600x399-2.jpg',
    href: 'Antelope_Canyon',
    desc: 'Antelope Canyon is a slot canyon in the American Southwest'
}, {
    image: 'ftse.svg',
    href: 'FTSE_All-Share_Index',
    desc: 'FTSE All-Share Index 1 year history May 16, 2019'
}, {
    image: 'gregrutherford.jpg',
    href: 'Greg_Rutherford',
    desc: 'Greg Rutherford wins long jump gold medal at the London Olympic Games on "Super Saturday", Aug 4, 2012 (composite image)'
}, {
    image: 'henrymoore.jpg',
    href: 'Henry_Moore',
    desc: 'A detail from Henry Moore\'s "Large Upright Internal/External Form"'
}, {
    image: 'mushrooms.jpg',
    href: 'Mushroom',
    desc: 'Mushrooms at the Mercato di Rialto, Venezia'
}, {
    image: 'pasha.jpg',
    href: 'Tabby_cat',
    desc: 'Pasha, a tabby cat'
}, {
    image: 'peppers.jpg',
    href: 'Chili_pepper',
    desc: 'Chili peppers at the Mercato di Rialto, Venezia'
}, {
    image: 'photo.jpg',
    href: 'Panum_Building',
    desc: 'Roof detail from the Panum Building at the University of Copenhagen, Copenhagen, Denmark'
}, {
    image: 'stonehenge.jpg',
    href: 'Stonehenge',
    desc: 'Stonehenge is a prehistoric monument in Wiltshire, England'
}]

function showPhoto() {
    const imageNo = Math.floor(Math.random() * banner.length);
    document.getElementById('banner-image').setAttribute('src', IMAGE_BASE + banner[imageNo].image);
    const link = document.getElementById('banner-link')
    link.setAttribute('href', WIKI_BASE + banner[imageNo].href);
    link.setAttribute('target', banner[imageNo].href);
    document.getElementById('banner-description').innerHTML = banner[imageNo].desc;
    const now = new Date();
    document.getElementById('date').innerHTML = now.toString();
    // change to next image every 3 seconds
    setTimeout('showPhoto()', 3000);
}

window.onload = showPhoto;
