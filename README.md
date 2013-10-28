IRC Program Using JGroups

Cara menjalankan program Eksekusi program gunakan perintah `run` pada Netbeans IDE

**uji perintah /NICK tanpa parameter**
1. jalankan perintah /NICK pada terminal
2. program akan memberi output: `[SUCCESS] system has generate random username: 027-zxmlr5u7u0`

**uji perintah /JOIN dengan parameter channel**
1. jalankan perintah `/JOIN 027` pada terminal
2. program akan memberi output:
    `-------------------------------------------------------------------`
		`GMS: address=smanurung-42438, cluster=027, physical address=192.168.0.101:59991`
		`-------------------------------------------------------------------`
		`** View: [smanurung-42438|0] (1) [smanurung-42438]`
		`[SUCCESS] user has joined cluster '027'`

**uji mengirim pesan ke channel spesifik**
1. misalnya jalankan perintah `@027 hello_folks !`
2. program ini akan memberi output:
    `[SUCCESS] message sent to channel 027`
3. program lain yang join channel `027` akan mendapat output:
    `[027] (027-zxmlr5u7u0) hello_folks`

**uji mengirim pesan broadcast**
1. misalnya jalankan perintah `hello this is broadcast message`
2. program ini akan memberi output:
    `[SUCCESS] message broadcasted`
3. program lain yang join channel `027` akan mendapat output:
    `[027] (027-zxmlr5u7u0) hello_folks`

**uji meninggalkan channel**
1. jalankan perintah `/LEAVE 027` pada terminal
2. program ini akan memberi output:
    `[SUCCESS] user has left channel smanurung-42438`
3. program lain akan menerima output:
		`** View: [smanurung-44601|2] (1) [smanurung-44601]`

**uji keluar dari program**
1. jalankan perintah `/EXIT` pada terminal
2. program ini akan memberi output:
    `program stopped`
3. program lain tidak mendapat notifikasi apapun (karena sudah leave group sebelumnya)