const inputs = document.querySelectorAll('input')

const updateBtn = document.querySelector('.updateBtn')
const cancelBtn = document.querySelector('.cancelBtn')
const deleteBtn = document.querySelector('.deleteBtn')
cancelBtn.onclick = () => {
	history.back();
}

load();

async function load() {
	getAuthenticationReq() // authentication/principal.js 의 함수, promise로 return된 값
		.then(result => {
			let principal = result.data.user;
			let provider = result.data.user.provider;
			if (provider == "google" || provider == "naver") {
				inputs[0].disabled = true;
				inputs[1].disabled = true;
				inputs[2].disabled = true;
				inputs[0].placeholder = "사이트 연계계정일 경우 수정불가"
				inputs[1].placeholder = "사이트 연계계정일 경우 수정불가"
				inputs[2].placeholder = "사이트 연계계정일 경우 수정불가"

			}
			inputs[3].value = principal.name;
			inputs[4].value = principal.email;
		})
		.catch(error => {
			console.log(error)
		});
}

updateBtn.onclick = () => {
	if (inputs[0].value.length > 0 && inputs[1].value.length == 0) {
		alert("새 패스워드 란이 비었습니다.")
	} else if (inputs[0].value.length > 0 && inputs[1].value.length > 0 && inputs[2].value.length == 0) {
		alert("새 패스워드 확인 란이 비었습니다.")
	} else if (inputs[0].value.length > 0 && inputs[1].value != inputs[2].value) {
		alert("새 패스워드 확인 값이 다릅니다.")
	} else if (inputs[0].value.length == 0 && inputs[1].value.length > 0 && inputs[2].value.length > 0) {
		alert("이전 패스워드를 입력해야 합니다.")
	} else if (inputs[0].value == inputs[1].value && inputs[0].value > 0) {
		alert("바꾸려는 패스워드가 이전 패스워드와 같습니다.")
	} else {
		checkPassword(inputs[0].value)
			.then(() => {
				if (confirm("회원정보를 수정하시겠습니까?")) {
					updateUserByUsername();
				}
			})
			.catch(error => {
				alert("이전 패스워드 값이 잘못되었습니다.")
				console.log(error);
			});
	}
}

deleteBtn.onclick = () => {
	if (confirm("정말로 탈퇴하시겠습니까?")) {
		deleteUserByUsername();
	 }
	 
}


async function checkPassword(password) {

	let username;

	await getAuthenticationReq() // authentication/principal.js 의 함수, promise로 return된 값
		.then(result => {
			username = result.data.user.username;
		})
		.catch(error => {
			console.log(error)
		});

	const url = `/api/v1/account/checkpassword`;
	let option = {
		method: "POST",
		headers: {
			"Content-Type": "application/json"
		},
		body: JSON.stringify({
			"username": username,
			"password": password
		})
	};
	const response = await fetch(url, option);
	if (response.ok) {
		return response.json(); // promise로 return
	} else {
		throw new Error("Failed to get Authentication." + response);
	}
}

async function deleteUserByUsername() {
	let username;

	await getAuthenticationReq() // authentication/principal.js 의 함수, promise로 return된 값
		.then(result => {
			username = result.data.user.username;
		})
		.catch(error => {
			console.log(error)
		});

	let url = `/api/v1/account/delete`;
	let option = {
		method: "DELETE",
		headers: {
			"Content-Type": "application/json"
		},
		body: JSON.stringify({
			"username": username,
		})
	};
	await fetch(url, option)
		.then(response => {
			if (response.ok) {
				return response.json();
			} else {
				throw new Error("비동기 처리 오류");
			}
		})
		.then(() => {
			alert('탈퇴가 완료되었습니다')
			location.replace("/logout");
		})
		.catch(error => { console.log(error) });

}

async function updateUserByUsername() {

	let username;

	await getAuthenticationReq() // authentication/principal.js 의 함수, promise로 return된 값
		.then(result => {
			username = result.data.user.username;
		})
		.catch(error => {
			console.log(error);
		});

	let url = `/api/v1/account/update`;
	let option = {
		method: "PUT",
		headers: {
			"Content-Type": "application/json"
		},
		body: JSON.stringify({
			"username": username,
			"password": inputs[1].value,
			"name": inputs[3].value,
			"email": inputs[4].value
		})
	};
	fetch(url, option)
		.then(response => { // 응답을 받음
			console.log(response);
			if (response.ok) { // 200~299 응답일시
				return response.json();
			} else {
				throw new Error(response.json());
			}
		})
		.then(() => { location.replace("/index") })
		.catch(error => console.log(error));
}