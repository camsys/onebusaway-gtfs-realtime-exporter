package org.onebusway.gtfs_realtime.exporter;
/**
 * Copyright (C) 2011 Google, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import java.io.IOException;
import java.net.URL;

import javax.inject.Inject;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.onebusaway.guice.jetty_exporter.ServletSource;

/**
 * Provide the delta in seconds from now to the last time we updated the feed message.
 *
 */
public class LastUpdateDeltaServlet extends HttpServlet implements ServletSource {
	private static final long serialVersionUID = 1L;
  private static final String CONTENT_TYPE = "application/json";
	protected GtfsRealtimeProvider _provider;
	private URL _url;
	
	  @Inject
	  public void setProvider(GtfsRealtimeProvider provider) {
	    _provider = provider;
	  }
	  
	  public void setUrl(URL url) {
	    _url = url;
	  }

	  @Override
	  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	      throws ServletException, IOException {
	      resp.setContentType(CONTENT_TYPE);
	      resp.getWriter().write(getMessage());
	  }
	  
    protected String getMessage() {
      // we don't answer to getMessage()
      return "" + (System.currentTimeMillis() - _provider.getLastUpdateTimestamp())/1000;
    }

    @Override
    public Servlet getServlet() {
      return this;
    }

    @Override
    public URL getUrl() {
      return _url;
    }
    
}
