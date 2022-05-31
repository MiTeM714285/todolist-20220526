const submitBtn = document.querySelector('.submitBtn');
const cancelBtn = document.querySelector('.cancelBtn');
const inputs = document.querySelectorAll('input');

submitBtn.onclick = async () => { // await을 쓰기 위한 async
	let result = await usernameCheck(inputs[0].value); // await은 해당 코드 처리이전에는 다음으로 넘어가지 않음
	if (result) { // 중복체크 우선
		if(result != false) {
			const url = `/api/v1/auth/signup`;
			const bodyObj = {
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
					alert(JSON.stringify(data));
				}
			})
			.catch (error => {
				console.log("응답실패")
				console.log(error);
			})
		}
	} else {

	}
}

async function usernameCheck(username) {
	const url = `/api/v1/auth/signup/username?username=${username}`;
	let responseData = false;
	
	await request(url)
	.then(result => { // 비어있지 않을시
		responseData = result.data; // result.data가 비어있음 여부 boolean
	})
	.catch(error => { // 비어있을시
		alert("아이디를 입력해주십시오.")
		console.log(error);
	})
	return responseData; // 최종 비어있음 여부의 boolean
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
