SELECT
	Q.ID AS quotationID,
    E.username AS employeeusername,
	Q.price, 
	C.username AS clientusername, 
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
		INNER JOIN (db_quotation_management.management M INNER JOIN db_quotation_management.employee E ON M.employeeID = E.ID)
			ON Q.ID = M.quotationID
		WHERE E.ID = 1;