/*
 * Copyright (C) 2016 hcadavid
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package edu.eci.cosw.jpa.sample;

import edu.eci.cosw.jpa.sample.model.Cliente;
import edu.eci.cosw.jpa.sample.model.ClienteId;
import edu.eci.cosw.jpa.sample.model.PolizaAprobada;
import edu.eci.cosw.jpa.sample.model.PolizaAprobadaId;
import edu.eci.cosw.jpa.sample.model.TipoPoliza;
import java.util.Date;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 *
 * @author hcadavid
 */
public class SimpleMainApp {

    public static void main(String a[]){
        SessionFactory sf=getSessionFactory();
        Session s=sf.openSession();
        Transaction tx=s.beginTransaction();
        
        Cliente c = new Cliente(new ClienteId(12345, "cc"), "Jhordy", "Calle 165", "3123467890");
        TipoPoliza tp =  (TipoPoliza) s.load(TipoPoliza.class, 1);
        PolizaAprobada pa = new PolizaAprobada(new PolizaAprobadaId(12345, "cc", 1), c, tp , new Date(), new Date());
        
        s.saveOrUpdate(c);
        s.saveOrUpdate(pa);
        
        // RECTIFICAMOS QUE ALLA QUEDADO AGREGADO CORRECTAMENTE
        
        Cliente cs = (Cliente) s.load(Cliente.class, new ClienteId(12345, "cc"));
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!CLIENTE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" +"\n"+c);
        PolizaAprobada pas = (PolizaAprobada)s.load(PolizaAprobada.class, new PolizaAprobadaId(12345, "cc", 1));
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!POLIZA APROVADA!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" +"\n"+pas);
        
        tx.commit();       
        s.close();
        sf.close();

    }

    public static SessionFactory getSessionFactory() {
        // loads configuration and mappings
        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
        ServiceRegistry serviceRegistry
                = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();

        // builds a session factory from the service registry
        SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        return sessionFactory;
    }

}
