/**
 * 
 */
package br.com.guilherme.webphotos.locator;

/**
 * @author Guilhe
 * 
 */
public class EJBDAOLocator extends BasicEJBLocator {
	private static EJBDAOLocator instance = new EJBDAOLocator();

	public static EJBDAOLocator getInstance() {
		return instance;
	}

	/*private static final String REMOTE_TURMASDAO = "java:comp/env/ejb/remote/TurmasDAO";
	private static final String REMOTE_MATRICULASDAO = "java:comp/env/ejb/remote/MatriculasDAO";
	private static final String REMOTE_MEMBERSHIPDAO = "java:comp/env/ejb/remote/MembershipDAO";
	private static final String REMOTE_CURSOSDAO = "java:comp/env/ejb/remote/CursosDAO";*/

	private EJBDAOLocator() {
		initEJBContext();
	}

    /*

	public CursosDAO getRemoteCursosDAO() throws WebfotosException {
		InitialContext initCtx = getEJBContext();
		Object ref = null;
		CursosDAO ejb = null;
		try {
			ref = initCtx.lookup(REMOTE_CURSOSDAO);
			CursosDAOHome cursosDAOHome = (CursosDAOHome) PortableRemoteObject
					.narrow(ref, CursosDAOHome.class);
			ejb = cursosDAOHome.create();
		} catch (Exception ex) {
			Debug.log(
					"Erro ao tentar localizar o EJB CursosDAO via remote interface"
							+ ex.getMessage(), 9);
			throw new WebfotosException(
					"Erro ao tentar localizar o EJB CursosDAO via remote interface"
							+ ex.getMessage(), ex);
		}
		return ejb;
	}

	public MatriculasDAO getRemoteMatriculasDAO() throws WebfotosException {
		InitialContext initCtx = getEJBContext();
		Object ref = null;
		MatriculasDAO ejb = null;
		try {
			ref = initCtx.lookup(REMOTE_MATRICULASDAO);
			MatriculasDAOHome matriculasDAOHome = (MatriculasDAOHome) PortableRemoteObject
					.narrow(ref, MatriculasDAOHome.class);
			ejb = matriculasDAOHome.create();
		} catch (Exception ex) {
			Debug.log(
					"Erro ao tentar localizar o EJB MatriculasDAO via remote interface"
							+ ex.getMessage(), 9);
			throw new WebfotosException(
					"Erro ao tentar localizar o EJB MatriculasDAO via remote interface"
							+ ex.getMessage(), ex);
		}
		return ejb;
	}

	public TurmasDAO getRemoteTurmasDAO() throws WebfotosException {
		InitialContext initCtx = getEJBContext();
		Object ref = null;
		TurmasDAO ejb = null;
		try {
			ref = initCtx.lookup(REMOTE_TURMASDAO);
			TurmasDAOHome turmasDAOHome = (TurmasDAOHome) PortableRemoteObject
					.narrow(ref, TurmasDAOHome.class);
			ejb = turmasDAOHome.create();
		} catch (Exception ex) {
			Debug.log(
					"Erro ao tentar localizar o EJB TurmasDAO via remote interface"
							+ ex.getMessage(), 9);
			throw new WebfotosException(
					"Erro ao tentar localizar o EJB TurmasDAO via remote interface"
							+ ex.getMessage(), ex);
		}
		return ejb;
	}

	public MembershipDAO getRemoteMembershipDAO() throws WebfotosException {
		InitialContext initCtx = getEJBContext();
		Object ref = null;
		MembershipDAO ejb = null;
		try {
			ref = initCtx.lookup(REMOTE_MEMBERSHIPDAO);
			MembershipDAOHome membershipDAOHome = (MembershipDAOHome) PortableRemoteObject
					.narrow(ref, MembershipDAOHome.class);
			ejb = membershipDAOHome.create();
		} catch (Exception ex) {
			Debug.log(
					"Erro ao tentar localizar o EJB MembershipDAO via remote interface"
							+ ex.getMessage(), 9);
			throw new WebfotosException(
					"Erro ao tentar localizar o EJB MembershipDAO via remote interface"
							+ ex.getMessage(), ex);
		}
		return ejb;
	}
     */
}
