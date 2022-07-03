let RPOrigin = "http://localhost:8090"
let loginDialog

function onReceiveMessage(event) {
    const message = JSON.parse(event.data)
    switch (message.Type){
        case "start":
            onReceiveT(message.t)
            break
        case "token":
            onReceiveToken(message.Token)
    }

}

function initXML(){
    if (window.XMLHttpRequest)
    {
        //  IE7+, Firefox, Chrome, Opera, Safari 浏览器执行代码
        return new XMLHttpRequest();
    }
    else
    {
        // IE6, IE5 浏览器执行代码
        return ActiveXObject("Microsoft.XMLHTTP");
    }
}

function doRegistration(){
    let text = document.getElementById("text");
    text.innerHTML = "Please input your nickname"
    document.getElementById("registration").style = ""
}


function onReceiveUpdateUsernameResult(xmlhttp, username){
    if (xmlhttp.readyState==4 && xmlhttp.status==200)
    {
        let data = JSON.parse(xmlhttp.responseText)
        if (data.result == "ok"){
            document.getElementById("registration").style = "display :none ;";
            let text = document.getElementById("text");
            text.innerHTML = "Welcome, " + username
            alert("Authentication Accomplished")
        }
    }
}

function updateUsername(){
    let username = document.getElementById("username").value;
    let url = RPOrigin + "/updateUsername"
    let xmlhttp = initXML();
    xmlhttp.onreadystatechange=function()
    {
        onReceiveUpdateUsernameResult(xmlhttp, username)
    }
    let body = {"username": username}
    xmlhttp.open("POST", url, true);
    xmlhttp.send(JSON.stringify(body));
}


function onReceiveAuthenticationResult(xmlhttp){
    if (xmlhttp.readyState==4 && xmlhttp.status==200)
    {
        loginDialog.close()
        let data = JSON.parse(xmlhttp.responseText)
        if (data.result == "ok"){
            let text = document.getElementById("text");
            text.innerHTML = "Welcome, " + data.username
            setTimeout(function() {
                    alert("Authentication Accomplished")
            }, 0)

        }else if (data.result == "register"){
            doRegistration()
        }else if (data.result == "error"){
            alert("Something is wrong")
        }

    }
}

function onReceiveToken(Token){
    let url = RPOrigin + "/authorization"
    let xmlhttp = initXML();

    xmlhttp.onreadystatechange=function()
    {
        onReceiveAuthenticationResult(xmlhttp)

    }
    let body = {"Token": Token}
    xmlhttp.open("POST", url, true);
    xmlhttp.send(JSON.stringify(body));
}



function onReceiveCert_RP(xmlhttp)
{
    if (xmlhttp.readyState==4 && xmlhttp.status==200)
    {
        let data = JSON.parse(xmlhttp.responseText)
        if (data.result == "ok"){
            let Cert_RP = data.Cert_RP
            let scope = data.scope
            if (Cert_RP!= null){
                let postMessageCert_RP = {"Type": "Cert", "Cert": Cert_RP, "scope": scope}
                loginDialog.postMessage(JSON.stringify(postMessageCert_RP), '*');
            }
        }
    }
}

function onReceiveT(t){
    let url = RPOrigin + "/startNegotiation"
    let xmlhttp = initXML();
    xmlhttp.onreadystatechange=function(){
        onReceiveCert_RP(xmlhttp)
    };
    xmlhttp.open("POST",url,true);
    let body = {"t": t}
    xmlhttp.send(JSON.stringify(body));
}



function startSSO()
{
    window.addEventListener('message', onReceiveMessage);
    loginDialog = window.open(
        `/redir`,
        'loginDialog',
        'width=600,height=400',
    );
}

startSSO();





