package cn.ncss.jym.graduate.service.impl.search;
/**
 * 找不到配置参数异常
 * @author kyrin
 *
 */
public class NotFindArgumentException extends IllegalArgumentException{

	private static final long serialVersionUID = 3923755654690865758L;
	
	public NotFindArgumentException(String message){
		super(message);
	}
}
