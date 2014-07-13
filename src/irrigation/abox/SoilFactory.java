package irrigation.abox;

import com.hp.hpl.jena.rdf.model.Model;

public class SoilFactory implements OntologyFactory
{
    Model model = null;
    Soil soil = null;
    
    public SoilFactory(Model model)
    {
        this.model = model; 
    }

    public Soil createSoil(SoilType soilType, CropType cropType)
    {
        this.soil = new Soil(model);
        
        setCrop(cropType);
        
        return this.soil;
    }
    
    private void setCrop(CropType cropType)
    {
        Crop crop = new Crop(model, cropType);
        this.soil.addCrop(crop);
    }
}
