/**
 * Copyright 2008 WebPhotos
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.sf.webphotos.locator;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

public class BasicEJBLocator {

	protected InitialContext cachedContext = null;
	public static String EJB_URL_PREFIXES = "org.jboss.naming";
	public static String EJB_CONTEXT_FACTORY = "org.jnp.interfaces.NamingContextFactory";
	public static String EJB_SERVER = "127.0.0.1:1099";

	private static Logger logger = Logger.getLogger(BasicEJBLocator.class);

	public void initEJBContext() {
		try {
			cachedContext = (InitialContext) getRemoteEJBsInitialContext();
		} catch (NamingException e) {
			logger.error("Erro fatal com context de EJBs", e);
		}
	}

	public InitialContext getEJBContext() {
		if (cachedContext == null) {
			initEJBContext();
		}
		return cachedContext;
	}

	public Context getRemoteEJBsInitialContext() throws NamingException {
		Hashtable<String, String> props = new Hashtable<String, String>();
		props.put(Context.INITIAL_CONTEXT_FACTORY, EJB_CONTEXT_FACTORY);
		props.put(Context.PROVIDER_URL, EJB_SERVER);
		props.put(Context.URL_PKG_PREFIXES, EJB_URL_PREFIXES);
		return new InitialContext(props);
	}

}
