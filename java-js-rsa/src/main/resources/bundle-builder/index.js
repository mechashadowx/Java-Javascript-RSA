// To update bundle.js
// run [browserify ./index.js -o ./../static/bundle.js] as root

const $ = require("jquery");
const crypto = require('crypto');
const NodeRSA = require('node-rsa');

$(async function(){
    $("body").html(
        `<div class='Scaffold'>
            <div class='Header'>
                <h1>Java,<h1>
                <h1>Javascript,<h1>
                <h1>RSA<h1>
            </div>
            <div class='Body'>
                <h4>This is a simple app to show you how to use Java, Javascript, and RSA encryption together :) [check the console]</h4>
            </div>
        </div>`
    );

    const serverMessage = await getServerMessage();
    const encryptedMessage = await encrypt(serverMessage);
    const serverCheck = await checkServerMessage(encryptedMessage);

    console.log('Server Message: %s', serverMessage);
    console.log('Encrypted Server Message: %s', encryptedMessage);
    console.log('Server Check: %s', serverCheck);
});

function getServerMessage(){
    return $.ajax({url: '/getMessage', type: 'GET'});
}

function checkServerMessage(encryptedMessage){
    return $.ajax({
        url: '/checkMessage',
        type: 'GET',
        data: {
            "encryptedMessage": encryptedMessage
        }
    });
}

function getKeyInfo(){
    return $.ajax({url: '/getPublicKeyInfo', type: 'GET'});
}

async function encrypt(text){
    const keyInfo = await getKeyInfo();
    const key = new NodeRSA();
    
    key.setOptions({encryptionScheme: 'pkcs1'});
    key.importKey({ n: Buffer.from(bnToB64(keyInfo.Mod), 'base64'), e: Buffer.from(bnToB64(keyInfo.Exp), 'base64') }, 'components-public');
    
    const publicKeyPem = key.exportKey(['public']);
    const buffer = Buffer.from(text, 'utf8');
    const encrypted = crypto.publicEncrypt({key:publicKeyPem, padding : crypto.constants.RSA_PKCS1_PADDING}, buffer);
    
    return encrypted.toString('base64');
}

// I copied this from somewhere.
function bnToB64(bn) {
    var hex = BigInt(bn).toString(16);
    if (hex.length % 2) { hex = '0' + hex; }
  
    var bin = [];
    var i = 0;
    var d;
    var b;
    while (i < hex.length) {
      d = parseInt(hex.slice(i, i + 2), 16);
      b = String.fromCharCode(d);
      bin.push(b);
      i += 2;
    }
  
    return window.btoa(bin.join(''));
}