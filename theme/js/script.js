const $ = el => document.querySelector(el);
const $$ = el => document.querySelectorAll(el);

let defaultWindowWidth = 0;
let defaultWindowHeight = 0;

const themes = {
    clair: 'light',
    sombre: 'dark'
};

window.onload = () => {
    const w = $('#window');

    if(w) {
        const rect = w.getBoundingClientRect();
        defaultWindowWidth = rect.width;
        defaultWindowHeight = rect.height;
    }

    setupResponsiveWindow();
    setupSlides();
    setupOS();
    setupTheme();
    createTable();

    const pauseBtn = $('#pause-btn');

    if(pauseBtn) {
        pauseBtn.addEventListener('click', () => {
            if(pauseBtn.innerText.toLowerCase() == 'pause') {
                pauseBtn.innerText = 'Reprendre';
            }

            else {
                pauseBtn.innerText = 'Pause';
            }
        });
    }


};

const createTable = () => {
    const table = document.createElement('table');
    const gridSize = 9;

    for(let i=0; i < gridSize +2; i++) {
        const row = document.createElement('tr');

        for(let j=0; j < gridSize +2; j++) {
            const td = document.createElement('td');

            if(
                ((i==1 || i==9) && j > 3 && j < 7) ||
                ((j==1 || j==9) && i > 3 && i < 7) ||
                ((i==2 || i==8) && j==5) ||
                ((j==2 || j==8) && i==5)
            ) {
                td.classList.add('black_tower-cell');
            }

            else if(
                (i==5 && j > 2 && j < 8) ||
                (j==5 && i > 2 && i < 8)
            ) {
                td.classList.add('white_tower-cell');

                if(j == 5 && i == 5) {
                    td.classList.add('king-cell');
                }
            }

            else if(
                (i == j && (i == 1 || i == 9)) ||
                (i == 1 && j == 9) ||
                (j == 1 && i == 9)
            ) {
                td.classList.add('door-cell');
            }

            if(i == 0 && j > 0 && j < 10) {
                td.innerText = String.fromCharCode(64+j);
            }

            else if(j == 0 && i > 0 && i < 10) {
                td.innerText = i;
            }

            if(i==1 && (j == 0 || j == 10)) {
                td.style.borderTop = '3px solid #bfa074';
            }

            row.appendChild(td);
        }

        table.appendChild(row);
    }

    $('#page-en-jeu').appendChild(table);
};



const setupSlides = () => {
    const wrapper = $('#window');
    const select = $('#slide-select');
    const articles = wrapper.querySelectorAll('article');

    // créer une option dans le select pour chaque <article> dans #window
    articles.forEach((article, i) => {
        if(i > 0)
            article.classList.add('hidden');
        else
            article.classList.add('active');

        const option = document.createElement('option');
        option.innerText = article.id.replace(/page\-/g, '').replace(/-/g, ' ');
        select.appendChild(option);
    });

    const changeSlide = () => {
        const option = select.querySelector('option:checked');
        const name = option.innerText.replace(/ /g, '-');
        const id = '#page-' + name;
        const active = $('#window article.active');
        active.classList.remove('active');
        active.classList.add('hidden');
        $(id).classList.add('active');
        $(id).classList.remove('hidden');

        if(name == 'raccourcis') {
            $('#get-help').style.display = 'none';
        } else {
            $('#get-help').style.display = 'block';
        }

    };

    // change de page
    select.addEventListener('change', changeSlide);
};

const setupOS = () => {
    const select = $('#os-select');
    const w = $('#window');

    if(!select || !w) return;

    const changeOS = () => {
        const option = select.querySelector('option:checked');
        const os = option.innerText.replace(/ /g, '-').toLowerCase();
        w.setAttribute('data-os', os);
    };

    select.addEventListener('change', changeOS);
    changeOS();
};

const setupTheme = () => {
    const select = $('#theme-select');
    const w = $('#window');

    if(!select || !w) return;

    const changeTheme = () => {
        const option = select.querySelector('option:checked');
        const os = option.innerText.replace(/ /g, '-').toLowerCase();
        w.setAttribute('data-theme', themes[os]);
    };

    select.addEventListener('change', changeTheme);
    changeTheme();
};

const setupResponsiveWindow = () => {
    const windowWidth = document.documentElement.clientWidth;
    const windowHeight = document.documentElement.clientHeight;

    const wd = $('#window');
    const s = $('#options-container');

    if(!wd || !s) return;

    const sRect = s.getBoundingClientRect();

    const m = sRect.height + 2 * sRect.top;
    let r = 1;

    if(defaultWindowHeight > m) {
        const h = windowHeight - 2 * m;
        r = h / defaultWindowHeight;
    }

    if(defaultWindowWidth > windowWidth) {
        const w = windowWidth - windowWidth/20;
        const rtmp = w / defaultWindowWidth;

        if(rtmp < r)
            r = rtmp;
    }

    if(r != 1) {
        wd.style.transform = `translate(-50%, -50%) scale(${r})`;
    }
};

window.onresize = () => {
    setupResponsiveWindow();
};