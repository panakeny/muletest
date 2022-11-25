%dw 2.0
output application/json
---
{
	code: "404",
	message: "Resource not found",
	moreInformation: {
		description: error.description  as String default "",
		
	}
}