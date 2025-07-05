package cl.duocuc.MicroServicioSucursal.servicio;



import cl.duocuc.MicroServicioSucursal.modelo.ModelSucursal;
import cl.duocuc.MicroServicioSucursal.repositorio.SucursalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SucursalServicio {

    @Autowired
    private SucursalRepository sucursalrepository;

    public ModelSucursal createSucursal(ModelSucursal sucursal){
        return sucursalrepository.save(sucursal);
    }


    public Optional<ModelSucursal> findbyId(Long id){
        return sucursalrepository.findById(id);
    }

    public List<ModelSucursal> getAll(){
        return sucursalrepository.findAll();
    }


    public List<ModelSucursal> deleteSucursal(Long id){
        if(sucursalrepository.existsById(id)){
            sucursalrepository.deleteById(id);
        }
            return sucursalrepository.findAll();
        }




    public  ModelSucursal updateSucursal(Long id,ModelSucursal udaptesucursal){
        return sucursalrepository.findById(id).map(sucursal -> {
            sucursal.setNombre(udaptesucursal.getNombre());
            sucursal.setDireccion(udaptesucursal.getDireccion());
            sucursal.setCiudad(udaptesucursal.getCiudad());
            sucursal.setTelefono(udaptesucursal.getTelefono());
            sucursal.setHorario_apertura(udaptesucursal.getHorario_apertura());
            sucursal.setHorario_cierre(udaptesucursal.getHorario_cierre());
            return sucursalrepository.save(sucursal);
        }).orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));


    }
}
