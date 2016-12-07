var http = require('http');
var fs = require('fs');
var urlparser = require('url');
var url = process.argv[2];
if(!url){
	console.log('wrong input!');
	process.exit();
}
if(url.substr(0,7)!='http://') url = "http://"+url;
var srvUrl = urlparser.parse(url);


var options = {
	hostname: srvUrl.hostname,
	port: 80,
	path: srvUrl.pathname,
	method: 'GET',
	headers: {
       'connection': 'keep-alive'
    }
};

analyze(url);

function analyze(url){
	var req = http.request(options,analyze_response);
	req.end();
	req.on('error',(e)=>{
		console.log(`Got error: ${e.message}`);
		process.exit();
	});
}

function methods(op){
	op.method="OPTIONS";
	var req = http.request(op,function(result){
		console.log('Allowed Methods \t%s' , result.headers.allow ? result.headers.allow : 'not Specified');
	});
	req.end();

}

var response = fs.createWriteStream('res.html');

function analyze_response(res){
	methods(options);
	stCode = res.statusCode;
	headers = res.headers;
	if(stCode==301 || stCode==307){
		console.log('Page Moved to %s',headers.location);
	}
	else if(stCode==404){
		console.log('page not found !');
	}
	else if(stCode==400){
		console.log('bad request !');
	}
	else if(stCode==403){
		console.log('forbidden !');
	}
	else if(stCode==401){
		console.log('Unauthorized !');
	}

	// console.log(headers);
	console.log('Web Server \t\t%s',headers.server ? headers.server : 'not Specified');
	console.log('Presistent Connenction \t%s','keep-alive'==headers.connection | 'Keep-Alive'==headers.connection ? "yes" : "No");
	console.log('Cache \t\t\t%s',headers['cache-control'] ? headers['cache-control'] : 'not Specified');
	console.log('Date \t\t\t%s',headers['date'] ? headers['date'] : 'not Specified');
	console.log('Expires \t\t%s',headers['expires'] ? headers['expires'] : 'not Specified');
	console.log('Last Modified \t\t%s',headers['last-modified'] ? headers['last-modified'] : 'not Specified');
	console.log('Authenticate \t\t%s',headers['www-authenticate'] ? headers['www-authenticate'] : 'no Auth');
	if(headers['set-cookie']){
		console.log('Cookies:')
		headers['set-cookie'].forEach(function(data){
			console.log("\t\t\t%s",data);
		});
	}
	


	res.pipe(response);
}




