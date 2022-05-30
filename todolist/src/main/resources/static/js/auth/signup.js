const submitBtn = document.querySelector('submitBtn');
const inputs = document.querySelectorAll('input');

submitBtn.onclick = () => {
	usernameCheck(); // 중복체크 우선
	
	const url = `/api/v1/auth/signup`;
	const body = {
			username : inputs[0].value,
			password : inputs[1].value,
			name : inputs[2].value,
			email : inputs[3].value
	};
	const option = {
		method:"POST",
		headers: {
			"Content-Type" : "application/json"
		},
		body:JSON.stringify(body)
	}
}

async function usernameCheck(username) {
	const url = `/api/v1/auth/username`;
	const body = {
			"username" : username,
	};
	const option = {
		method:"GET",
		headers:{
			"Content-Type" : "application/json"
		},
		body:JSON.stringify(body)
	}
	
	await request(url, option)
	.then(result => {
		console.log(result);
	})
	.catch(error => {
		console.log(error);
	})
}

async function request(url) {
	
	const response = await fetch(url);
	if(response.ok) {
		return response.json()
	} else {
		throw new Error("response Error : " + response);
	}
}

async function request(url, options) {
	
	const response = await fetch(url, options);
	if(response.ok) {
		return response.json()
	} else {
		throw new Error("response Error : " + response);
	}
}