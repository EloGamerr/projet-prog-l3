const $ = el => document.querySelector(el);
const $$ = el => document.querySelectorAll(el);

let defaultWindowWidth = 0;
let defaultWindowHeight = 0;
const canvas = document.createElement('canvas');
let ctx;
let buttonsTo = [];
let pages = [];
let needToDraw = false;
let circleRadius, _y, _x;
let inACircle = null;
let mouseX, mouseY;
let pagesToDraw = {};


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
    setupOS();
    setupTheme();
    createTable();
    setupAutomaton();
    setupSlides();
    
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

        if(['raccourcis', 'relation-des-pages'].includes(name)) {
            $('#get-help').style.display = 'none';
        } else {
            $('#get-help').style.display = 'block';
        }

        if(name === 'relation-des-pages') {
            needToDraw = true;
            draw();
        }
        else
            needToDraw = false;
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

        /* const rr = wd.getBoundingClientRect();
        canvas.width = rr.width;
        canvas.height = rr.height; */
    }
};

const setupAutomaton = () => {
    const searchItems = ['button', 'li'];
    const expectedAttribute = 'to';

    buttonsTo = Array.from($$(searchItems.join(', '))).filter(item => item.hasAttribute(expectedAttribute)).map((item, i) => ({
        from: item.closest('article').id.replace(/^page\-/, ''),
        to: item.getAttribute(expectedAttribute)
    })).filter((v, i, a) => a.findIndex(t => t.from === v.from && t.to === v.to) === i);

    pages = Array.from($$('article[id^="page-"]')).map(article => article.id.replace(/^page\-/, ''));
    
    const page = document.createElement('article');
    page.id = `page-relation-des-pages`;

    ctx = canvas.getContext('2d');
    const size = $('#wrapper').getBoundingClientRect();

    canvas.width = size.width;
    canvas.height = size.height;
    canvas.style.width = '100%';
    canvas.style.height = '100%';
    canvas.style.background = '#ddd';
    
    page.appendChild(canvas);
    $('#wrapper').appendChild(page);

    circleRadius = Math.min(canvas.width, canvas.height) / pages.length / 3;
    const margin = 5 * circleRadius / pages.length;
    _y = canvas.height / 2;
    _x = canvas.width / 2 - (circleRadius + margin) * (pages.length - 1);
    const allX = [_x];

    for(let i=1; i < pages.length; i++)
        allX.push(allX[i-1] + 2 * (circleRadius + margin));

    let jAlt = 0;

    pages.forEach((name, ii) => {
        const splittedName = name.split('-');
        let fontSize = circleRadius;
        ctx.font = `${fontSize}px serif`;

        while(fontSize > 6 && (Math.max(...splittedName.map(t => ctx.measureText(t).width)) >= 1.4*circleRadius || fontSize*splittedName.length >= circleRadius))
            ctx.font = `${--fontSize}px serif`;
        
        const x = allX[ii], y = _y;
        const nameY = y - ((splittedName.length == 1) ? 0 : (splittedName.length+fontSize)/2.5);

        // transitions
        pagesToDraw[name] = {
            splittedName, fontSize, nameY, x, y,
            transitions: []
        };

        const idx = pages.indexOf(name);
        const h = (360 / pages.length * idx) % 360;
        const transitions = buttonsTo.filter(i => i.from === name);

        transitions.forEach(t => {
            const i = pages.indexOf(t.to);

            if(i === -1)
                return;

            let x0, y0, x1, y1, x2, y2, sens = 0;

            x0 = x;
            x1 = x + (allX[i] - x) / 2;
            x2 = allX[i];
            y0 = y1 = y2 = y;

            // left -> right
            if(idx == i-1) {
                x0 += circleRadius + 2;
                x2 -= circleRadius + 2;
                sens = 1;
            }
            // left <- right
            else if(idx == i+1) {
                x0 -= circleRadius + 2;
                x2 += circleRadius + 2;
                sens = -1;
            }

            else {
                const k = (jAlt++%2 == 0)? 1 : -1;
                y0 = y2 = y - k * circleRadius;
                y1 = y0 + -k * (i+1) * 60;
            }

            const _  = jAlt%2==0? 0 : 1;

            const rotation = (sens != 0)?
                sens * 90 * Math.PI / 180 :
                Math.PI * _ + ((_==0?-1:1) * (i - idx > 0 ? -1 : 1) * 30 * Math.PI/180);

            pagesToDraw[name].transitions.push({ h, x0, y0, x1, y1, x2, y2, rotation });
        });
    });

    canvas.onmousemove = canvasMouseMove;
};


const circle = (x, y, r) => {
    ctx.beginPath();
        ctx.arc(x, y, r, 0, 2*Math.PI);
        ctx.stroke();
    ctx.closePath();
};

const drawPage = name => {
    const { x, y, fontSize, nameY, splittedName } = pagesToDraw[name];

    ctx.strokeStyle = '#000';
    ctx.fillStyle = '#000';
    ctx.lineWidth = 1;
    ctx.textAlign = 'center';

    ctx.globalAlpha = 1 / ((inACircle === null || (name === inACircle))? 1 : 5);

    // circle
    circle(x, y, circleRadius);

    // name
    ctx.font = `${fontSize}px serif`;
    splittedName.forEach((t, i) => {
        ctx.beginPath();
            ctx.fillText(t, x, nameY + i*fontSize);
            ctx.fill();
        ctx.closePath();
    });

    ctx.globalAlpha = 1;
};

const drawTransitions = name => {
    pagesToDraw[name].transitions.forEach(t => {    
        const { h, x0, y0, x1, y1, x2, y2, rotation } = t;
        
        ctx.strokeStyle = `hsl(${h}, 50%, 50%)`;
        ctx.globalAlpha = 0.7 / ((inACircle === null || (name === inACircle))? 1 : 5);
        ctx.lineWidth = 1;
        
        ctx.beginPath();
            ctx.moveTo(x0, y0);
            ctx.quadraticCurveTo(x1, y1, x2, y2);
            ctx.stroke();
        ctx.closePath();
        
        ctx.save();
            ctx.translate(x2, y2);
            ctx.rotate(rotation);
            ctx.beginPath();
                ctx.moveTo(0, 0);
                ctx.lineTo(-5, 5);
                ctx.lineTo(0, 0);
                ctx.lineTo(5, 5);
                ctx.stroke();
            ctx.closePath();
        ctx.restore();

        ctx.globalAlpha = 1;
    });
};

const draw = () => {
    ctx.clearRect(0, 0, canvas.width, canvas.height);

    pages.forEach((p) => drawPage(p));
    pages.forEach((p) => drawTransitions(p));

    if(needToDraw)
        requestAnimationFrame(draw);
};

const mouseInCircle = (a, b) => {
    return Math.hypot(a.x - b.x, a.y - b.y) <= circleRadius;
};

const canvasMouseMove = e => {
    mouseX = e.clientX - canvas.getBoundingClientRect().x;
    mouseY = e.clientY - canvas.getBoundingClientRect().y;

    const k = Object.keys(pagesToDraw);
    let b = false;
    
    for(let i=0; i < k.length; i++) {
        const page = k[i];
        if(mouseInCircle({ x: mouseX, y: mouseY }, { x: pagesToDraw[page].x, y: pagesToDraw[page].y })) {
            if(inACircle === null) {
                inACircle = page;
            }
            b = true;
            return;
        }
    }

    if(!b && inACircle !== null) {
        inACircle = null;
    }
};

window.onresize = () => {
    setupResponsiveWindow();
};