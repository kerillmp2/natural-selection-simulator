package action;

import map.ObjectMap;
import map.objects.MapObject;
import map.PixelMap;

public interface Action {
    void resolve(MapObject performer, ObjectMap map);
}
