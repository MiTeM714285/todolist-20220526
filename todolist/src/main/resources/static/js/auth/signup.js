const submitBtn = document.querySelector('.submitBtn');
const cancelBtn = document.querySelector('.cancelBtn');
const inputs = document.querySelectorAll('input');

submitBtn.onclick = async () => { // await을 쓰기 위한 async
	let idResult = await usernameCheck(inputs[0].value); // await은 해당 코드 처리이전에는 다음으로 넘어가지 않음
	let passwordResult = await passwordCheck()
	if (idResult && passwordResult) { // 아이디 및 비밀번호 체크 우선
		const url = `/api/v1/auth/signup`;
		const bodyObj = {
			username : inputs[0].value,
			password : inputs[1].value,
			name : inputs[3].value,
			email : inputs[4].value
		};
		const option = {
			method:"POST",
			headers: {
				"Content-Type" : "application/json"
			},
			body:JSON.stringify(bodyObj)
		}
		
		request(url, option)
		.then(result=> {
			console.log("응답")
			console.log(result);
			return result.data;
		})
		.then(data => {
			if(data == true) {
				alert("회원가입 성공")
				window.location.replace("/auth/signin");
			} else {
				let keys = Object.keys(data)
				let values = Object.values(data)
				let warning = '';
				for(let i = 0; i < keys.length; i++) {
					if(keys[i] == "username") {
						warning += "아이디는 " + values[i] + ".\n";
					} else if(keys[i] == "password") {
						warning += "패스워드는 " + values[i] + ".\n";
					} else if(keys[i] == "name") {
						warning += "이름은 " + values[i] + ".\n";
					} else if(keys[i] == "email") {
						warning += "이메일은 " + values[i] + ".\n";
					}
				}
				alert(warning.slice(0,-1));
			}
		})
		.catch (error => {
			console.log("응답실패")
			console.log(error);
		})
	} else {

	}
}

cancelBtn.onclick = () => {
	location.href='/auth/signin'
}


async function usernameCheck(username) {
	const url = `/api/v1/auth/signup/username?username=${username}`;
	let responseData = false;
	
	await request(url)
	.then(result => { // 중복이 아닐시
		responseData = result.data; // result.data가 중복 여부 boolean
	})
	.catch(error => { // 중복일시
		alert("중복된 아이디입니다");
		console.log(error);
	})
	return responseData; // 최종 중복 여부의 boolean
}

async function passwordCheck() {
	if(inputs[1].value == inputs[2].value) {
		return true;
	} else {
		alert("두 패스워드 입력란이 같지 않습니다.");
		return false;
	}
}

/*
async function request(url) {
	
	const response = await fetch(url);
	if(response.ok) {
		return response.json()
	} else {
		throw new Error("response Error : " + response);
	}
}
*/

async function request(url, options) {
	const response = await fetch(url, options);
	if(response.ok) { // 200번 return된 경우
		return response.json()
	} else if(response.json().then(result => {
		return result.code;
	}) == -1 ){ // CustomValidationApiException.java 에서의 -1이 포함되어 return된 경우
		return response.json()
	} else {
		throw new Error("response Error : " + response);
	}
}
