/**
 * 세션정보 요청
 */

async function getAuthenticationReq() {
	const url = "/api/v1/auth/authentication";
	
	const response = await fetch(url);
	if(response.ok) {
		return response.json(); // promise로 return
	} else {
		throw new Error("Failed to get Authentication." + response)
	}
}