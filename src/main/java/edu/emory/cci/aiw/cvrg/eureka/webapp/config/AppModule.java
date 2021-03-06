package edu.emory.cci.aiw.cvrg.eureka.webapp.config;

/*
 * #%L
 * Eureka WebApp
 * %%
 * Copyright (C) 2012 - 2013 Emory University
 * %%
 * This program is dual licensed under the Apache 2 and GPLv3 licenses.
 * 
 * Apache License, Version 2.0:
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
 * 
 * GNU General Public License version 3:
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
import edu.emory.cci.aiw.cvrg.eureka.webapp.comm.clients.EurekaClinicalRegistryClientProvider;
import edu.emory.cci.aiw.cvrg.eureka.webapp.comm.clients.EurekaClinicalUserClientProvider;
import edu.emory.cci.aiw.cvrg.eureka.webapp.comm.clients.EurekaClientProvider;
import edu.emory.cci.aiw.cvrg.eureka.webapp.comm.clients.EurekaClinicalPhenotypeClientProvider;
import edu.emory.cci.aiw.cvrg.eureka.webapp.comm.clients.EurekaClinicalProtempaClientProvider;
import edu.emory.cci.aiw.cvrg.eureka.webapp.client.WebappRouterTable;
import com.google.inject.AbstractModule;
import com.google.inject.servlet.SessionScoped;

import org.eurekaclinical.common.comm.clients.RouterTable;
import org.eurekaclinical.eureka.client.EurekaClient;
import org.eurekaclinical.protempa.client.EurekaClinicalProtempaClient;
import org.eurekaclinical.phenotype.client.EurekaClinicalPhenotypeClient;
import org.eurekaclinical.registry.client.EurekaClinicalRegistryClient;
import org.eurekaclinical.standardapis.props.CasEurekaClinicalProperties;
import org.eurekaclinical.user.client.EurekaClinicalUserClient;

/**
 *
 * @author hrathod
 */
class AppModule extends AbstractModule {

    private final WebappProperties webappProperties;
    private final EurekaClinicalUserClientProvider userClientProvider;
    private final EurekaClinicalProtempaClientProvider etlClientProvider;
    private final EurekaClientProvider servicesClientProvider;
    private final EurekaClinicalRegistryClientProvider registryClientProvider;
    private final EurekaClinicalPhenotypeClientProvider phenotypeClientProvider;        

    AppModule(WebappProperties webappProperties, EurekaClientProvider inServicesClientProvider, EurekaClinicalProtempaClientProvider inEtlClientProvider, EurekaClinicalUserClientProvider inUserClient, EurekaClinicalRegistryClientProvider inRegistryClient,
		EurekaClinicalPhenotypeClientProvider inPhenotypeClientProvider                
        ) {
        assert webappProperties != null : "webappProperties cannot be null";
        this.webappProperties = webappProperties;
        this.userClientProvider = inUserClient;
        this.servicesClientProvider = inServicesClientProvider;
        this.etlClientProvider = inEtlClientProvider;
        this.registryClientProvider = inRegistryClient;
        this.phenotypeClientProvider = inPhenotypeClientProvider;
    }

    @Override
    protected void configure() {
        bind(RouterTable.class).to(WebappRouterTable.class).in(SessionScoped.class);
        bind(WebappProperties.class).toInstance(this.webappProperties);
        bind(CasEurekaClinicalProperties.class).toInstance(this.webappProperties);
        bind(EurekaClient.class).toProvider(this.servicesClientProvider).in(SessionScoped.class);
        bind(EurekaClinicalProtempaClient.class).toProvider(this.etlClientProvider).in(SessionScoped.class);
        bind(EurekaClinicalUserClient.class).toProvider(this.userClientProvider).in(SessionScoped.class);
        bind(EurekaClinicalRegistryClient.class).toProvider(this.registryClientProvider).in(SessionScoped.class);
        bind(EurekaClinicalPhenotypeClient.class).toProvider(this.phenotypeClientProvider).in(SessionScoped.class);                
    }

}
