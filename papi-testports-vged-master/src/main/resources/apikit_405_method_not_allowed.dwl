%dw 2.0
output application/json
---
{
	code: "405",
	message: "Method not allowed",
	moreInformation: {
		description: error.description  as String default "",
		
	}
}