let CryptoJS = require("crypto-js");

let Crypto = {
	functions : {
		encrypt: function(text) {
			return CryptoJS.AES.encrypt(text, config.ENCRYPT_DECRYPT_PASSWORD);
		},

		decrypt: function(text) {
			let bytes  = CryptoJS.AES.decrypt(text.toString(), config.ENCRYPT_DECRYPT_PASSWORD);
			let plaintext = bytes.toString(CryptoJS.enc.Utf8);

			return plaintext;
		}
	}
}