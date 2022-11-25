%dw 2.0
output application/json
---
{
	code: "406",
	message: "Unsupported media type",
	moreInformation: {
		description: error.description  as String default "",
		
	}
}