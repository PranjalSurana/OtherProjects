
const WIKI_BASE = 'https://en.wikiquote.org/wiki/';

const quote = [{
    text: `No doubt the reason is that character cannot be developed in ease and quiet. Only through experience of trial and suffering can the soul be strengthened, 
vision cleared, ambition inspired, and success achieved.`,
    href: 'Helen_Keller',
    author: 'Helen Keller'
}, {
    text: `In our sun-down perambulations, of late, through the outer parts of Brooklyn, we have observed several parties of youngsters playing "base", 
a certain game of ball ... Let us go forth awhile, and get better air in our lungs. Let us leave our close rooms ... the game of ball is glorious.`,
    href: 'Walt_Whitman',
    author: 'Walt Whitman'
}, {
    text: `But a human being who's true to himself - whoever himself may be - is immortal;and all the atomic bombs of all the antiartists in spacetime 
will never civilize immortality.`,
    href: 'E._E._Cummings',
    author: 'E. E. Cummings'
}, {
    text: 'The most important persuasion tool you have in your entire arsenal is integrity.',
    href: 'Zig_Ziglar',
    author: 'Zig Ziglar'
}, {
    text: 'To invent, you need a good imagination and a pile of junk.',
    href: 'Thomas_Edison',
    author: 'Thomas Edison'
}, {
    text: `Les grandes personnes ne comprennent jamais rien toutes seules, et c'est fatigant, pour les enfants, de toujours leur donner des explications.
(Grown-ups never understand anything by themselves, and it is tiresome for children to be always and forever explaining things to them.)`,
    href: 'Antoine_de_Saint_Exupéry',
    author: 'Antoine de Saint Exupéry'
}, {
    text: `Without courage we cannot practice any other virtue with consistency. We can't be kind, true, merciful, generous, or honest.`,
    href: 'Maya_Angelou',
    author: 'Maya Angelou'
}, {
    text: `A vida é tão bela que a mesma idéia da morte precisa de vir primeiro a ela, antes de se ver cumprida.
(Life is so beautiful that even the idea of death must be born before it can be realized.)`,
    href: 'Joaquim_Maria_Machado_de_Assis',
    author: 'Joaquim Maria Machado de Assis'
}, {
    text: `It is unwise to be too sure of one's own wisdom. It is healthy to be reminded that the strongest might weaken and the wisest might err.`,
    href: 'Mahatma_Gandhi',
    author: 'Mohandas K. (Mahatma) Ghandi'
}, {
    text: 'Only in growth, reform, and change, paradoxically enough, is true security to be found.',
    href: 'Anne_Morrow_Lindbergh',
    author: 'Anne Morrow Lindbergh'
}, {
    text: `No one is born hating another person because of the colour of his skin, or his background, or his religion. 
People must learn to hate, and if they can learn to hate, they can be taught to love, for love comes more naturally to the human heart than its opposite.`,
    href: 'Nelson_Mandela',
    author: 'Nelson Mandela'
}, {
    text: `Two rights don't equal a left.`,
    href: 'Roald_Dahl',
    author: 'Roald Dahl'
}]

function showQuote() {
    const quoteNo = Math.floor(Math.random() * quote.length);
    document.getElementById('quotation').innerHTML = quote[quoteNo].text;
    // The exercise doesn't ask for links, but we already had the code from the example ;)
    const link = document.getElementById('author-link')
    link.setAttribute('href', WIKI_BASE + quote[quoteNo].href);
    link.setAttribute('target', quote[quoteNo].href);
    link.innerHTML = quote[quoteNo].author;
    const now = new Date();
    const options = { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric', hour: 'numeric', minute: 'numeric', second: 'numeric', timeZoneName: 'long' };
    document.getElementById('date').innerHTML = now.toLocaleString('pt-BR', options);
    // change to next image every 5 seconds, adjust this if you need more time to read the quotes
    setTimeout('showQuote()', 5000);
}

window.onload = showQuote;
