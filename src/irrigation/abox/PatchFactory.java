package irrigation.abox;

import com.hp.hpl.jena.rdf.model.Model;

public class PatchFactory implements OntologyFactory
{
    Model model = null;
    public PatchFactory(Model model)
    {
        this.model = model;
    }

    public Patch makeBlankPatch()
    {
        Patch patch = new Patch(model);
        
        return patch;
    }
}
