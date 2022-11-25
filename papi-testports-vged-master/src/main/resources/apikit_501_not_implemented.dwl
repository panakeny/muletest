%dw 2.0
output application/json
---
{
	code: "501",
	message: "Not Implemented",
	moreInformation: {
		description: error.description  as String default "",
		
	}
}