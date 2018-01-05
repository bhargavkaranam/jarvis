let CryptoJS = require("crypto-js");

let Crypto = {
	functions : {
		encrypt: function(text) {
			return CryptoJS.AES.encrypt('my message', config.ENCRYPT_DECRYPT_PASSWORD);
		},

		decrypt: function(text) {
			let bytes  = CryptoJS.AES.decrypt(ciphertext.toString(), config.ENCRYPT_DECRYPT_PASSWORD);
			let plaintext = bytes.toString(CryptoJS.enc.Utf8);

			return plaintext;
		}
	}
}