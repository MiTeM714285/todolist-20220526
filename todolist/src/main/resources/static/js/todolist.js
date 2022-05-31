/**
 * 
 */

const toDoTemplateBlock = document.querySelector('.toDoTemplateBlock');
const memberBtns = document.querySelectorAll('.memberBtn');
const idArray = [];

// 정보수정 버튼 클릭에 관하여
memberBtns[0].onclick = () => {
	location.href="/account";
}

// 로그아웃 버튼 클릭에 관하여
memberBtns[1].onclick = () => {
	location.replace("/logout");
}


load();

function load() {
	
	getAuthenticationReq() // authentication/principal.js 의 함수, promise로 return된 값
	.then(result => {
		let principal = result.data.user;
		let usercode = principal.usercode;
		
		let url = `/api/v1/todo/list/${usercode}`
		fetch(url)
			.then(response => {
				if (response.ok) {
					return response.json();
				} else {
					throw new Error("비동기 처리 오류");
				}
			})
			.then(result => { // 받아온 ResponseEntity객체.
				setDate(); // 오늘 날짜 및 할 일 갯수 표시
				getTodoListList(result.data, null); // 응답받은 CustomResponseDto의 data
				getTodoListItems();
			})
			.catch(error => { console.log(error) });
		
	})
	.catch(error => {
		console.log(error)
	});

}

function getTodoListItems() { // 각 버튼을 클릭했을때의 이벤트 담당
	
	// 삭제버튼 클릭에 관하여
	const toDoItemBlockRemove = document.querySelectorAll('.toDoItemBlock-remove'); // 만들어진 toDoItem-remove 객체등록
	for (let i = 0; i < toDoItemBlockRemove.length; i++) {
		toDoItemBlockRemove[i].onclick = () => {
			let idByIndex = idArray[i];
			idArray.slice(i, 1)
			console.log(idByIndex);
			deleteToDoList(idByIndex);
		}
	}

	// + 버튼 클릭에 관하여
	const toDoPlus = document.querySelector('.toDoPlus');
	toDoPlus.onclick = () => {
		InsertVisible_PlusDisappear();
	}

	// Cancel버튼 클릭에 관하여
	const toDoInsertBlockCancel = document.querySelector('.toDoInsertBlock-cancel');
	toDoInsertBlockCancel.onclick = () => {
		PlusVisible_InsertDisappear();
	}

	// Insert버튼 클리에 관하여
	const toDoInsertBlockInsert = document.querySelector('.toDoInsertBlock-insert');
	toDoInsertBlockInsert.onclick = () => {
		const toDoInsertBlockInput = document.querySelector('.toDoInsertBlock-input');
		const content = toDoInsertBlockInput.value;
		if (content.length > 42) {
			alert("현재 " + content.length + "자로, 42자 내로 작성 가능합니다.")
		} else {
			InsertToDoList(content);
		}
	}

	// 체크 버튼 클릭에 관하여
	const toDoItemBlockCheckCircle = document.querySelectorAll('.toDoItemBlock-checkCircle');
	for (let i = 0; i < toDoItemBlockCheckCircle.length; i++) {
		toDoItemBlockCheckCircle[i].onclick = () => {
			let idByIndex = idArray[i];
			getToDoListOne(idByIndex)
				.then(result => {
					let toDoData = result.data
					if(toDoData.isdone == 0) {
						ToDoListIsDone(idByIndex);
					} else {
						ToDoListIsUnDone(idByIndex);
					}
				})
				.catch(error => {
					console.log(error)
				})
		}
	}
	
	// 수정 버튼 클릭에 관하여
	const toDoItemBlockModify = document.querySelectorAll('.toDoItemBlock-modify');
	for (let i = 0; i < toDoItemBlockModify.length; i++) {
	toDoItemBlockModify[i].onclick = () => {
		const toDoItemUpdateBlock = document.querySelectorAll('.toDoItemUpdateBlock');
		const toDoItemBlock = toDoItemUpdateBlock[i].querySelector('.toDoItemBlock');
		const toDoUpdateBlock = toDoItemUpdateBlock[i].querySelector('.toDoUpdateBlock');
		const toDoItemBlockText = toDoItemBlock.querySelector('.toDoItemBlock-text');
		const toDoUpdateBlockInput = toDoUpdateBlock.querySelector('.toDoUpdateBlock-input');
		toDoItemBlock.style.display = "none"; // 기존 텍스트뷰는 사라지고
		toDoUpdateBlock.style.display = ""; // 입력창을 보이도록
		
		//일반 Todo표시글의 텍스트내용을 업데이트 수정텍스트란으로 옮기기
		toDoUpdateBlockInput.value = toDoItemBlockText.textContent;
		}
	}
	
	// 수정 취소 버튼 클릭에 관하여
	const toDoUpdateBlockCancel = document.querySelectorAll('.toDoUpdateBlock-cancel');
	for (let i = 0; i < toDoUpdateBlockCancel.length; i++) {
	toDoUpdateBlockCancel[i].onclick = () => {
			ViewVisible_UpdateDisappear(i);
		}
	}
	
	// 수정 완료 버튼 클릭에 관하여
	const toDoUpdateBlockUpdate = document.querySelectorAll('.toDoUpdateBlock-update');
	for (let i = 0; i < toDoUpdateBlockUpdate.length; i++) {
	toDoUpdateBlockUpdate[i].onclick = () => {
			const toDoItemUpdateBlock = document.querySelectorAll('.toDoItemUpdateBlock');
			const toDoUpdateBlockInput = toDoItemUpdateBlock[i].querySelector('.toDoUpdateBlock-input');
			let idByIndex = idArray[i];
			if (toDoUpdateBlockInput.value.length > 42) {
				alert("현재 " + toDoUpdateBlockInput.value.length + "자로, 42자 내로 작성 가능합니다.")
			} else {
				const toDoItemBlock = toDoItemUpdateBlock[i].querySelector('.toDoItemBlock');
				const toDoItemBlockText = toDoItemBlock.querySelector('.toDoItemBlock-text');
				if(toDoItemBlockText.textContent == toDoUpdateBlockInput.value) { // 수정 전 값과 수정 후 값이 같다면
					ViewVisible_UpdateDisappear(i);
				} else {
					modifyTodo(idByIndex, toDoUpdateBlockInput.value);
				}
			}
		}
	}
	

}

function InsertToDoList(content) {
	
	getAuthenticationReq() // authentication/principal.js 의 함수, promise로 return된 값
	.then(result => {
		let principal = result.data.user;
		usercode = principal.usercode;
		
		let url = `/api/v1/todo/${usercode}`;
		let option = {
			method: "POST",
			headers: {
				"Content-Type": "application/json"
			},
			body: JSON.stringify({
				content: content
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
			.then(() => { 
				load();
			})
			.catch(error => console.log(error));
		
	})
	.catch(error => {
		console.log(error)
	});

}

function InsertVisible_PlusDisappear() {
	const toDoPlusBlock = document.querySelector('.toDoPlusBlock');
	const toDoInsertBlock = document.querySelector('.toDoInsertBlock');
	toDoPlusBlock.style.display = 'none'; // + 버튼을 안보이게하고
	toDoInsertBlock.style.display = ''; // 입력란을 보이도록
}


function PlusVisible_InsertDisappear() {
	const toDoPlusBlock = document.querySelector('.toDoPlusBlock');
	const toDoInsertBlock = document.querySelector('.toDoInsertBlock');
	toDoPlusBlock.style.display = ''; // + 입력란을 안보이게하고
	toDoInsertBlock.style.display = 'none'; // + 버튼을 보이도록
}

// 일반 Todo표시글 ON 업데이트 수정텍스트란 OFF
function ViewVisible_UpdateDisappear(i) {
	const toDoItemUpdateBlock = document.querySelectorAll('.toDoItemUpdateBlock');
	const toDoItemBlock = toDoItemUpdateBlock[i].querySelector('.toDoItemBlock');
	const toDoUpdateBlock = toDoItemUpdateBlock[i].querySelector('.toDoUpdateBlock');
	toDoItemBlock.style.display = ""; // 일반 Todo표시글은 보이고
	toDoUpdateBlock.style.display = "none"; // 업데이트 수정 텍스트란은 사라지도록
}



async function getToDoListOne(id) {
	const url = `/api/v1/todo/${id}`;
	
	const response = await fetch(url);
	if(response.ok) {
		return response.json(); // promise로 return
	} else {
		throw new Error("Failed to get Authentication." + response)
	}
}

async function getIsUndoneCount() {
	
	let usercode = {};
	await getAuthenticationReq() // authentication/principal.js 의 함수, promise로 return된 값
	.then(result => {
		let principal = result.data.user;
		usercode = principal.usercode;
	})
	.catch(error => {
		console.log(error)
	});
	const url = `/api/v1/todo/isUndoneCount/${usercode}`;
	
	const response = await fetch(url);
	if(response.ok) {
		return response.json(); // promise로 return
	} else {
		throw new Error("Failed to get Authentication." + response)
	}
	

}

async function getToDoListOne(id) {
	const url = `/api/v1/todo/${id}`;
	
	const response = await fetch(url);
	if(response.ok) {
		return response.json(); // promise로 return
	} else {
		throw new Error("Failed to get Authentication." + response)
	}
}

function deleteToDoList(id) {
	let url = `/api/v1/todo/${id}`;
	let option = {
		method: "DELETE"
	};
	fetch(url, option)
		.then(response => {
			if (response.ok) {
				return response.json();
			} else {
				throw new Error("비동기 처리 오류");
			}
		})
		.then(() => { load(); })
		.catch(error => { console.log(error) });
}

function modifyTodo(id, content) {
	let url = `/api/v1/todo/${id}`;
	let option = {
		method: "PUT",
		headers : {
			"Content-Type" : "application/json"
		},
		body: JSON.stringify ({
			content : content
		})
	};
	fetch(url, option)
	.then(response => { // 응답을 받음
		console.log(response);
		if(response.ok) { // 200~299 응답일시
			return response.json();
		} else {
			throw new Error(response.json());
		}
	})
	.then(() =>{load();})
	.catch(error => console.log(error));
}

function ToDoListIsDone(id) {
	let url = `/api/v1/todo/isdone/${id}`;
	
	let option = {
		method: "PUT",
	};
	fetch(url, option)
	.then(response => { // 응답을 받음
		console.log(response);
		if(response.ok) { // 200~299 응답일시
			return response.json();
		} else {
			throw new Error(response.json());
		}
	})
	.then(() => { load(); })
	.catch(error => console.log(error));
}

function ToDoListIsUnDone(id) {
	let url = `/api/v1/todo/isundone/${id}`;
	
	let option = {
		method: "PUT",
	};
	fetch(url, option)
	.then(response => { // 응답을 받음
		console.log(response);
		if(response.ok) { // 200~299 응답일시
			return response.json();
		} else {
			throw new Error(response.json());
		}
	})
	.then(() => { load(); })
	.catch(error => console.log(error));
}

function setDate() {
	const today = new Date();
    const dateString = today.toLocaleDateString('ko-KR', { // 오늘 날짜 가져오기
        year:'numeric',
        month:'long',
        day: 'numeric'
    });
	const dayName = today.toLocaleDateString('ko-KR', { // 오늘 요일 가져오기
	    weekday:'long'
	});

	getAuthenticationReq() // auth/principal.js 의 함수, promise로 return된 값
	// 첫번째, principal의 사용자 name 가져오기
	.then(result => {
		let principal = result.data.user;
			let name = principal.name;
		getIsUndoneCount()
		// 두번째, isUndoneCount 가져오기
		.then(result => {
			let isUndoneCount = result.data
			
			const toDoHeadBlock = document.querySelector('.toDoHeadBlock')
			const days = `
				<h1>${dateString}</h1>
			    <div class="toDoHeadBlock-day">${dayName}</div>
				<div class="toDoHeadBlock-tasks-left">${name}님의 할 일 ${isUndoneCount}개 남음</div>
			`
			toDoHeadBlock.innerHTML = days;
		})
		.catch(error => {
			console.log(error)
		})
	})
	.catch(error => {
		console.log(error)
	})
}

function getTodoListList(data) { // 응답받은 CustomResponseDto의 data로 게시글리스트 출력 메소드
	const toDoListBlock = toDoTemplateBlock.querySelector('.toDoListBlock');
	idArray.length = 0; // 일단 배열 초기화
	let toDoItemBlock = ``

	for (let i = 0; i < data.length; i++) { // 실질적 글리스트 출력
		idArray.push(data[i].id)
		toDoItemBlock += `
			<div class="toDoItemUpdateBlock">
				<div class="toDoItemBlock">
					<div class="toDoItemBlock-checkCircle"><i class="fa-solid fa-check"></i></div>
					<div class="toDoItemBlock-text">${data[i].content}</div>
					<div class="toDoItemBlock-modify"><i class="fa-solid fa-pen-to-square"></i></div>
					<div class="toDoItemBlock-remove"><i class="fa-solid fa-trash-can"></i></div>
				</div>
				<div class="toDoUpdateBlock">
					<div class="toDoUpdateBlock-checkCircle"><i class="fa-solid fa-check"></i></div>
					<input class="toDoUpdateBlock-input" type="text"/>
					<div class="toDoUpdateBlock-update"><i class="fa-solid fa-file-pen"></i></div>
					<div class="toDoUpdateBlock-cancel"><i class="fa-solid fa-xmark"></i></div>
				</div>
			</div>
		`
	}

	// 삽입란
		toDoItemBlock += `
		<div class="toDoInsertBlock">
			<input class="toDoInsertBlock-input" type="text"/>
			<div class="toDoInsertBlock-insert"><i class="fa-solid fa-file-pen"></i></div>
			<div class="toDoInsertBlock-cancel"><i class="fa-solid fa-xmark"></i></div>
		</div>
	`

	// 추가버튼
	toDoItemBlock += `
		<div class="toDoPlusBlock">
			<div class="toDoPlus"><i class="fa-solid fa-plus"></i></div>
		</div>
	`

	toDoListBlock.innerHTML = toDoItemBlock;
	
	const toDoInsertBlock = toDoListBlock.querySelector('.toDoInsertBlock');
	toDoInsertBlock.style.display = "none"; // 삽입란은 우선 숨기기
	const toDoUpdateBlock = toDoListBlock.querySelectorAll('.toDoUpdateBlock');
	for(let i = 0; i<toDoUpdateBlock.length; i++) {
		toDoUpdateBlock[i].style.display = "none"; // 각 Todo의 업데이트란 역시 우선 숨기기
	}

	// isdone = 1인 Todo 체크 테두리 입히기
	const toDoItemBlockCheckCircle = toDoTemplateBlock.querySelectorAll('.toDoItemBlock-checkCircle');
	const toDoUpdateBlockCheckCircle = toDoTemplateBlock.querySelectorAll('.toDoUpdateBlock-checkCircle');
	for (let i = 0; i < toDoItemBlockCheckCircle.length; i++) {
		if (data[i].isdone > 0) {
			toDoItemBlockCheckCircle[i].style.border = "1px solid #20c997";
			toDoItemBlockCheckCircle[i].style.color = "#20c997";
			toDoUpdateBlockCheckCircle[i].style.border = "1px solid #20c997";
			toDoUpdateBlockCheckCircle[i].style.color = "#20c997";
		}
	}
}