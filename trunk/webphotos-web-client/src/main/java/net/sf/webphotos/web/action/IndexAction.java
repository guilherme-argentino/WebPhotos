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
package net.sf.webphotos.web.action;

import net.sf.webphotos.dao.jpa.AlbunsDAO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author gsilva
 */
public class IndexAction extends Action {

    private static final String SUCCESS = "success";
    private static final Log log = LogFactory.getLog(IndexAction.class);

    private AlbunsDAO albunsDAO;

    /**
     * This is the action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     * @throws java.lang.Exception
     * @return
     */
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        log.info("Starting Action \"" + this.getClass().getName() + "\"");

        request.setAttribute("requestURL", request.getRequestURL());

        request.setAttribute("albunsVO", albunsDAO.findAll());

        return mapping.findForward(SUCCESS);
    }

    /**
     * @return the albunsDAO
     */
    public AlbunsDAO getAlbunsDAO() {
        return albunsDAO;
    }

    /**
     * @param albunsDAO the albunsDAO to set
     */
    public void setAlbunsDAO(AlbunsDAO albunsDAO) {
        this.albunsDAO = albunsDAO;
    }
}
