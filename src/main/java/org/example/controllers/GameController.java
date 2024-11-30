package org.example.controllers;

import org.example.threads.CocineroThread;
import org.example.threads.ComensalThread;
import org.example.threads.MeseroThread;
import org.example.factory.RestaurantEntityFactory;
import org.example.models.Comensal;
import org.example.models.MonitorComidas;
import org.example.models.MonitorMesas;
import org.example.models.MonitorOrdenes;
import org.example.vistas.ChefVista;
import org.example.vistas.ClienteVista;
import org.example.vistas.MeseroVista;

import java.util.ArrayList;
import java.util.List;

public class GameController {
    private final RestaurantEntityFactory restaurantEntityFactory = new RestaurantEntityFactory();

    private final ChefVista chefVista = new ChefVista(this.restaurantEntityFactory);
    public GameController(){}
    public void run(){
        List<Comensal> comensales = new ArrayList<>();
        List<ClienteVista> clienteVistaList = new ArrayList<>();

        this.restaurantEntityFactory.crearFondo();
        this.chefVista.crearChef(680, 240);
        MonitorMesas monitorMesas = new MonitorMesas(clienteVistaList);
        MonitorOrdenes monitorOrdenes = new MonitorOrdenes();
        MonitorComidas monitorComidas = new MonitorComidas();



        for (int i = 0; i < 10; i++) {
            comensales.add(new Comensal(i + 1));
            ClienteVista cliente = new ClienteVista(this.restaurantEntityFactory, i+1);
            cliente.crearCliente(0, 240);
            clienteVistaList.add(cliente);
        }

        MeseroVista meseroVista = new MeseroVista(this.restaurantEntityFactory);
        meseroVista.crearMesero(0, -120);

        new Thread(new MeseroThread(monitorOrdenes, monitorComidas, comensales, clienteVistaList, meseroVista,monitorMesas)).start();


        new Thread(new CocineroThread(monitorOrdenes, monitorComidas, comensales, meseroVista)).start();


        for (Comensal comensal : comensales) {
            new Thread(new ComensalThread(comensal, monitorMesas, monitorOrdenes, clienteVistaList)).start();
        }
    }
}
