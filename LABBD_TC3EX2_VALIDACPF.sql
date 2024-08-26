USE LABBD_TC3EX2_CLIENTE

CREATE PROCEDURE sp_validPrimeiro (@cpf CHAR(11), @validUm BIT OUTPUT)
AS
	DECLARE @Soma INT,
			@cont INT,
			@sub INT
	SET @cont = 10
	SET @Soma = 0
	SET @sub = 0
	WHILE (@cont  <= 2)
	BEGIN
		SET @Soma = (CAST(SUBSTRING(@cpf,@sub,1))*@cont) + @Soma
		SET @sub = @sub +1
	END
	IF ((@Soma % 11) < 2) 
	BEGIN
		IF(CAST(SUBSTRING(@cpf,9,1)) = 0)
		BEGIN
			SET @validUm = 1
		END
	END
	ELSE
	BEGIN
		IF(CAST(SUBSTRING(@cpf,9,1)) = (11-(@Soma % 11)))
		BEGIN
			SET @validUm = 1
		END
		ELSE
		BEGIN
			SET @validUm = 0
		END
	END