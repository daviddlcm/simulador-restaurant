package org.example.controllers;

import org.example.Threads.CocineroThread;
import org.example.Threads.ComensalThread;
import org.example.Threads.MeseroThread;
import org.example.Threads.RecepcionistaThread;
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
        this.restaurantEntityFactory.crearFondo();
        this.chefVista.crearChef(680, 240);
        MonitorMesas monitorMesas = new MonitorMesas();
        MonitorOrdenes monitorOrdenes = new MonitorOrdenes();
        MonitorComidas monitorComidas = new MonitorComidas();

        List<Comensal> comensales = new ArrayList<>();
        List<ClienteVista> clienteVistaList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            comensales.add(new Comensal(i + 1));
            ClienteVista cliente = new ClienteVista(this.restaurantEntityFactory, i+1);
            cliente.crearCliente(0, 240);
            clienteVistaList.add(cliente);
        }

        new Thread(new RecepcionistaThread(monitorMesas, comensales, clienteVistaList)).start();


        MeseroVista meseroVista = new MeseroVista(this.restaurantEntityFactory);
        meseroVista.crearMesero(0, -120);

        new Thread(new MeseroThread(monitorOrdenes, monitorComidas, comensales, clienteVistaList, meseroVista)).start();

        new Thread(new CocineroThread(monitorOrdenes, monitorComidas, comensales, meseroVista)).start();


        for (Comensal comensal : comensales) {
            new Thread(new ComensalThread(comensal, monitorMesas, monitorOrdenes, clienteVistaList)).start();
        }
    }
}
