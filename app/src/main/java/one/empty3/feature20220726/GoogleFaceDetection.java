package one.empty3.feature20220726;

import androidx.camera.camera2.interop.ExperimentalCamera2Interop;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javaAnd.awt.Point;
import one.empty3.library.Point3D;
import one.empty3.library.Polygon;
import one.empty3.library.StructureMatrix;

@ExperimentalCamera2Interop public class GoogleFaceDetection {
    public double[] TRANSPARENT = new double[] {0,0,0};
    private List<FaceData> dataFaces;

    public class FaceData {
        @ExperimentalCamera2Interop public class Surface {
            private int colorFill;
            private int colorContours;
            private int colorTransparent;
            private int surfaceId;
            private Polygon polygon;
            private PixM contours;

            public Surface(int surfaceId, Polygon polygon, PixM contours, int colorFill, int colorContours, int colorTransparent) {
                this.surfaceId = surfaceId;
                this.polygon = polygon;
                this.contours = contours;
                this.colorFill = colorFill;
                this.colorContours = colorContours;
                this.colorTransparent = colorTransparent;
            }

            public int getSurfaceId() {
                return surfaceId;
            }

            public void setSurfaceId(int surfaceId) {
                this.surfaceId = surfaceId;
            }

            public Polygon getPolygon() {
                return polygon;
            }

            public void setPolygon(Polygon polygon) {
                this.polygon = polygon;
            }

            public PixM getContours() {
                return contours;
            }

            public void setContours(PixM contours) {
                this.contours = contours;
            }

            public int getColorFill() {
                return colorFill;
            }

            public void setColorFill(int colorFill) {
                this.colorFill = colorFill;
            }

            public int getColorContours() {
                return colorContours;
            }

            public void setColorContours(int colorContours) {
                this.colorContours = colorContours;
            }

            public int getColorTransparent() {
                return colorTransparent;
            }

            public void setColorTransparent(int colorTransparent) {
                this.colorTransparent = colorTransparent;
            }

            public boolean isContaning(Point pInPicture) {
                StructureMatrix<Point3D> boundRect2d = polygon.getBoundRect2d();
                double[] values = contours.getValues((int) (double) (pInPicture.x - boundRect2d.getElem(0).get(0)),
                        (int) (double) (pInPicture.y - boundRect2d.getElem(0).get(1)));
                return values.equals(TRANSPARENT);
            }
        }

        private List<Surface> faceSurfaces;

        public List<Surface> getFaceSurfaces() {
            return faceSurfaces;
        }

        public void setFaceSurfaces(List<Surface> faceSurfaces) {
            this.faceSurfaces = faceSurfaces;
        }

        public FaceData(List<Surface> faceSurfaces) {
            this.faceSurfaces = faceSurfaces;
            if(faceSurfaces==null)
                this.faceSurfaces = new ArrayList<>();
        }
        public FaceData() {
            this.faceSurfaces = new ArrayList<>();
        }
    }
    public GoogleFaceDetection(List<FaceData> dataFaces) {
        this.dataFaces = dataFaces;
    }
    public GoogleFaceDetection() {
        dataFaces = new ArrayList<>();
    }

    public List<FaceData> getDataFaces() {
        return dataFaces;
    }

    public void setDataFaces(List<FaceData> dataFaces) {
        this.dataFaces = dataFaces;
    }

    public Polygon getPolygon(Point pInPicture) {
        final FaceData.Surface[] surface = {null};
        for (FaceData dataFace : dataFaces) {
            dataFace.getFaceSurfaces().forEach(new Consumer<FaceData.Surface>() {
                @Override
                public void accept(FaceData.Surface surface1) {
                    if(surface1.isContaning(pInPicture))
                        surface[0] = surface1;
                }
            });
        }
        if(surface[0]!=null)
            return surface[0].getPolygon();
        else
            return null;
    }

}
