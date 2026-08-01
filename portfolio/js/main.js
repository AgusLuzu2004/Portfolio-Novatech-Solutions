"use strict";

const preloader = document.getElementById("preloader");

const header = document.querySelector("header");

const menuBtn = document.querySelector(".menu-mobile");

const navLinks = document.querySelector(".nav-links");

const overlay = document.querySelector(".menu-overlay");

const btnArriba = document.getElementById("btn-arriba");

const navItems = document.querySelectorAll(".nav-links a");

window.addEventListener("load", () => {

    setTimeout(() => {

        preloader.style.opacity = "0";

        preloader.style.visibility = "hidden";

    }, 500);

});

function abrirMenu() {

    navLinks.classList.add("active");

    overlay.classList.add("active");

}

function cerrarMenu() {

    navLinks.classList.remove("active");

    overlay.classList.remove("active");

}

menuBtn.addEventListener("click", abrirMenu);

overlay.addEventListener("click", cerrarMenu);

navItems.forEach(link => {

    link.addEventListener("click", cerrarMenu);

});

window.addEventListener("scroll", () => {

    if (window.scrollY > 60) {

        header.style.background = "rgba(15,23,42,.95)";

        header.style.boxShadow = "0 10px 25px rgba(0,0,0,.25)";

    } else {

        header.style.background = "rgba(15,23,42,.75)";

        header.style.boxShadow = "none";

    }

});

window.addEventListener("scroll", () => {

    if (window.scrollY > 500) {

        btnArriba.classList.add("visible");

    } else {

        btnArriba.classList.remove("visible");

    }

});

btnArriba.addEventListener("click", () => {

    window.scrollTo({

        top: 0,

        behavior: "smooth"

    });

});

const elementosAnimados = document.querySelectorAll(
    ".fade, .tech-card, .info-card, .otro-card, .timeline-item, .contacto-card, .cert-card, .stat-card"
);

const observer = new IntersectionObserver((entries) => {

    entries.forEach(entry => {

        if (entry.isIntersecting) {

            entry.target.classList.add("visible");

        }

    });

}, {

    threshold: 0.15

});

elementosAnimados.forEach(elemento => {

    observer.observe(elemento);

});

const typingElement = document.querySelector(".typing");

const textos = [

    "Desarrollador Java Junior",
    "Analista de Datos",
    "Power BI Developer",
    "SQL & MySQL",
    "Python Developer"

];

let textoActual = 0;
let letraActual = 0;
let borrando = false;

function escribirTexto(){

    if(!typingElement) return;

    const texto = textos[textoActual];

    if(!borrando){

        typingElement.textContent = texto.substring(0, letraActual);

        letraActual++;

        if(letraActual > texto.length){

            borrando = true;

            setTimeout(escribirTexto,1800);

            return;

        }

    }else{

        typingElement.textContent = texto.substring(0, letraActual);

        letraActual--;

        if(letraActual < 0){

            borrando = false;

            textoActual++;

            if(textoActual >= textos.length){

                textoActual = 0;

            }

        }

    }

    setTimeout(escribirTexto,borrando ? 40 : 80);

}

escribirTexto();

const contadores = document.querySelectorAll(".stat-card h3");

const contadorObserver = new IntersectionObserver(entries=>{

    entries.forEach(entry=>{

        if(entry.isIntersecting){

            const contador = entry.target;

            const objetivo = parseInt(contador.dataset.numero);

            let valor = 0;

            const incremento = Math.max(1, Math.ceil(objetivo / 60));

            const intervalo = setInterval(()=>{

                valor += incremento;

                if(valor >= objetivo){

                    valor = objetivo;

                    clearInterval(intervalo);

                }

                contador.textContent = valor;

            },25);

            contadorObserver.unobserve(contador);

        }

    });

},{threshold:.5});

contadores.forEach(contador=>{

    contadorObserver.observe(contador);

});

const secciones = document.querySelectorAll("section[id]");

window.addEventListener("scroll",()=>{

    let scroll = window.scrollY + 140;

    secciones.forEach(section=>{

        const top = section.offsetTop;

        const height = section.offsetHeight;

        const id = section.getAttribute("id");

        if(scroll >= top && scroll < top + height){

            navItems.forEach(link=>{

                link.classList.remove("active");

            });

            const activo = document.querySelector(`.nav-links a[href="#${id}"]`);

            if(activo){

                activo.classList.add("active");

            }

        }

    });

});

const imagenProyecto = document.querySelector(".proyecto-imagen img");

const capturas = [

    "assets/screenshots/login.png",
    "assets/screenshots/dashboard.png",
    "assets/screenshots/clientes.png",
    "assets/screenshots/productos.png",
    "assets/screenshots/ventas.png",
    "assets/screenshots/reportes.png"

];

let capturaActual = 0;

if(imagenProyecto){

    setInterval(()=>{

        capturaActual++;

        if(capturaActual >= capturas.length){

            capturaActual = 0;

        }

        imagenProyecto.style.opacity = 0;

        setTimeout(()=>{

            imagenProyecto.src = capturas[capturaActual];

            imagenProyecto.style.opacity = 1;

        },300);

    },4000);

}

const modal = document.getElementById("cv-modal");

const abrirCV = document.querySelector(".btn-cv");

const cerrarCV = document.querySelector(".cerrar-modal");

if(abrirCV){

    abrirCV.addEventListener("click",(e)=>{

        e.preventDefault();

        modal.style.display="flex";

    });

}

if(cerrarCV){

    cerrarCV.addEventListener("click",()=>{

        modal.style.display="none";

    });

}

window.addEventListener("click",(e)=>{

    if(e.target===modal){

        modal.style.display="none";

    }

});

const temaBtn = document.querySelector(".theme-toggle");

const temaGuardado = localStorage.getItem("theme");

if(temaGuardado==="light"){

    document.body.classList.add("light-mode");

}

temaBtn?.addEventListener("click",()=>{

    document.body.classList.toggle("light-mode");

    const modo=

        document.body.classList.contains("light-mode")

        ? "light"

        : "dark";

    localStorage.setItem("theme",modo);

});

const barra=document.querySelector(".progress-bar");

window.addEventListener("scroll",()=>{

    const scrollTop=document.documentElement.scrollTop;

    const altura=

        document.documentElement.scrollHeight-

        document.documentElement.clientHeight;

    const progreso=(scrollTop/altura)*100;

    if(barra){

        barra.style.width=progreso+"%";

    }

});

const cursor=document.querySelector(".cursor");

const cursor2=document.querySelector(".cursor2");

if(cursor && cursor2){

    document.addEventListener("mousemove",(e)=>{

        cursor.style.left=e.clientX+"px";

        cursor.style.top=e.clientY+"px";

        cursor2.style.left=e.clientX+"px";

        cursor2.style.top=e.clientY+"px";

    });

}

document.querySelectorAll('a[href^="#"]').forEach(enlace => {

    enlace.addEventListener("click", function (e) {

        const destino = document.querySelector(this.getAttribute("href"));

        if (!destino) return;

        e.preventDefault();

        destino.scrollIntoView({

            behavior: "smooth",
            block: "start"

        });

    });

});

document.addEventListener("keydown", (e) => {

    if (e.key === "Escape" && modal) {

        modal.style.display = "none";

    }

});

const imagenes = document.querySelectorAll("img");

const lazyObserver = new IntersectionObserver((entries, observer) => {

    entries.forEach(entry => {

        if (entry.isIntersecting) {

            const img = entry.target;

            if (img.dataset.src) {

                img.src = img.dataset.src;

            }

            img.classList.add("visible");

            observer.unobserve(img);

        }

    });

}, {

    threshold: .1

});

imagenes.forEach(img => {

    if (img.dataset.src) {

        lazyObserver.observe(img);

    }

});

const tarjetas = document.querySelectorAll(

    ".tech-card, .info-card, .otro-card, .contacto-card, .cert-card"

);

tarjetas.forEach(card => {

    card.addEventListener("mousemove", e => {

        const rect = card.getBoundingClientRect();

        const x = e.clientX - rect.left;

        const y = e.clientY - rect.top;

        card.style.setProperty("--x", x + "px");

        card.style.setProperty("--y", y + "px");

    });

});

const copyright = document.querySelector(".copyright");

if (copyright) {

    const año = new Date().getFullYear();

    copyright.innerHTML =
        `© ${año} Agustín Luzuriaga. Todos los derechos reservados.`;

}

document.addEventListener("keyup", e => {

    if (e.key === "Tab") {

        document.body.classList.add("keyboard-navigation");

    }

});

document.addEventListener("mousedown", () => {

    document.body.classList.remove("keyboard-navigation");

});

console.log("%cPortfolio desarrollado por Agustín Luzuriaga",

"color:#2563eb;font-size:18px;font-weight:bold;");

console.log("%cGracias por visitar mi portfolio.",

"color:#38bdf8;font-size:14px;");