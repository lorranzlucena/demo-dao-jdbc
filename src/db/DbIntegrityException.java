package db;

public class DbIntegrityException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Excess�o personalizada de integridade referencial caso apague algum objeto do
	 * banco de dados que tem chave estrangheira em outra tabela
	 * 
	 * @param msg
	 */
	public DbIntegrityException(String msg) {
		super(msg);

	}

}
