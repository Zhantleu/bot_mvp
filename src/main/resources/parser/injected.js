setInterval(parse, 2000);

const BUSY_CHECK = 'box-shadow: rgb(13, 202, 242) 0px 0px 0px 3px;'
const BUSY_ACCOUNT = 'box-shadow: rgb(94, 53, 177) 0px 0px 0px 3px;'
const FREE = 'box-shadow: rgb(76, 175, 80) 0px 0px 0px 3px;'

const SEAT_TYPES = {
    FREE: 'FREE',
    BUSY: 'BUSY'
}

function parse() {
    const seats = new Map();
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
                seats.set(seat, seatStatus)
            }
        }
    }

    const mapAsc = new Map([...seats.entries()].sort((a, b) => a[0] - b[0]));

    console.log(mapAsc)


    fetch('https://bot-mvp.herokuapp.com/seats', {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
        },
    })
        .then(response => response.text())
        .then(text => console.log(text))
}