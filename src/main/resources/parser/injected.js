setInterval(parse, 2000);

const BUSY_CHECK = 'box-shadow: rgb(13, 202, 242) 0px 0px 0px 3px;'
const BUSY_ACCOUNT = 'box-shadow: rgb(94, 53, 177) 0px 0px 0px 3px;'
const FREE = 'box-shadow: rgb(76, 175, 80) 0px 0px 0px 3px;'

const SEAT_TYPES = {
    FREE: 'FREE',
    BUSY: 'BUSY'
}

function parse() {
    const seats = [];
    const table = document.getElementsByTagName("cap-workstation-map-grid")[0];
    for (const childNode of table.childNodes) {
        for (const childNodeElement of childNode.childNodes) {
            if (childNodeElement.className === 'workstation-inner') {
                const status = childNodeElement.style.cssText
                const seat = parseInt(childNodeElement.innerText);
                let seatStatus;
                if (status === BUSY_CHECK || status === BUSY_ACCOUNT) {
                    seatStatus = SEAT_TYPES.BUSY
                } else {
                    seatStatus = SEAT_TYPES.FREE
                }
                seats.push({'number': seat, 'status': seatStatus})
            }
        }
    }

    console.log(JSON.stringify(seats))
    fetch('https://bot-mvp.herokuapp.com/seats', {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(seats)
    })
        .then(response => response.text())
        .then(text => console.log(text))
}


setInterval(parseInstagramMessage, 5000);


function parseInstagramMessage() {
    const messages = [];
    const divMessages = document.querySelectorAll('[aria-label="Chats"]')[0].childNodes[0].childNodes[0].childNodes[0].childNodes[1].childNodes[0].childNodes
    for (const childNode of divMessages) {
        const childNode1 = childNode.childNodes[0];
        const elementsByClassName = childNode1.getElementsByClassName("x9f619 x1n2onr6 x1ja2u2z x78zum5 x2lah0s x1qughib x6s0dn4 xozqiw3 x1q0g3np")[0].childNodes;

        const name = elementsByClassName[1].getElementsByClassName("x1lliihq x193iq5w x6ikm8r x10wlt62 xlyipyv xuxw1ft")[0].innerText;
        let isNotAnswered = false;
        if (!!elementsByClassName[2].getElementsByClassName("x6s0dn4 xzolkzo x12go9s9 x1rnf11y xprq8jg x9f619 x3nfvp2 xl56j7k x1tu34mt xdk7pt x1xc55vz x1emribx")[0]) {
            isNotAnswered = true;
        }

        messages.push({'name': name, 'isNotAnswered': isNotAnswered})
    }

    fetch('https://bot-mvp.herokuapp.com/instagram-messages', {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(messages)
    })
        .then(response => response.text())
        .then(text => console.log(text))
}