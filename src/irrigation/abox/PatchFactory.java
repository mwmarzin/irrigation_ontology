package irrigation.abox;

import com.hp.hpl.jena.rdf.model.Model;

//TODO refactor into AbstractOntClassFactory
public class PatchFactory implements OntologyFactory
{
    Model model = null;
    SoilFactory sFactory = null;
    Patch patch = null;
    public PatchFactory(Model model)
    {
        this.model = model;
        sFactory = new SoilFactory(model);
    }
    
    public Patch makePatch(SoilType soilType, CropType cropType )
    {
        this.patch = new Patch(model);
        
        setSoil(soilType, cropType);
        
        return this.patch;
    }
    
    public Patch makePatch(SoilType soilType, CropType cropType, Location location )
    {
        this.patch = new Patch(model);
        
        setSoil(soilType, cropType);
        setLocation(location);
        
        return this.patch;
    }
    
    private void setLocation(Location location)
    {
        patch.setLocation(location);
    }
    
    private void setSoil(SoilType soilType, CropType cropType)
    {
        Soil soil = sFactory.createSoil(soilType,cropType);
        
        this.patch.setSoil(soil);
    }
}
