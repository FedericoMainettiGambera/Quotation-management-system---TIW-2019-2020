SELECT
	Q.ID,
	Q.price, 
	C.username, 
	P.name AS productname, 
	P.image,
    O.name as optionname,
    O.type
		FROM db_quotation_management.quotation Q
		INNER JOIN db_quotation_management.client C
			ON Q.clientID = C.ID
		INNER JOIN db_quotation_management.product P
			ON Q.productID = P.ID
		INNER JOIN (db_quotation_management.selectedoption SO INNER JOIN db_quotation_management.option O ON SO.optionID = O.ID)
			ON Q.ID = SO.quotationID
		WHERE Q.clientID = 1;