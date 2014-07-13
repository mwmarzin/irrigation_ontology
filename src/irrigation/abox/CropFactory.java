package irrigation.abox;

import com.hp.hpl.jena.rdf.model.Model;

public class CropFactory implements OntologyFactory
{
    private Model model = null;
    private Crop crop = null;
    public CropFactory(Model model)
    {
        this.model = model;
    }

    public Crop makeCrop(CropType cropType)
    {
        
        return this.crop;
    }
}
