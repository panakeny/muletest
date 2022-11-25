%dw 2.0
output application/json
---
{
	code: "413",
	message: " Request Entity Too Large",
	moreInformation: {
		description: error.description  as String default "",
		
	}
}