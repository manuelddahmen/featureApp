package one.empty3.feature20220726;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PointF;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.camera.camera2.interop.ExperimentalCamera2Interop;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import one.empty3.library.Lumiere;
import one.empty3.library.Point3D;
import one.empty3.library.Polygon;
import one.empty3.library.StructureMatrix;

@ExperimentalCamera2Interop public class GoogleFaceDetection implements Parcelable {
    static GoogleFaceDetection instance;
    private FaceData.Surface selectedSurface;
    public static double[] TRANSPARENT = Lumiere.getDoubles(Color.BLACK);
    private List<FaceData> dataFaces;

    public static GoogleFaceDetection getInstance() {
        if(instance==null)
            instance = new GoogleFaceDetection();
        return instance;
    }

    protected GoogleFaceDetection(Parcel in) {
        in.readTypedObject(new Creator<Object>() {
            @Override
            public Object createFromParcel(Parcel parcel) {
                GoogleFaceDetection googleFaceDetection = GoogleFaceDetection.getInstance();
                int numFaces = parcel.readInt();
                for (int face = 0; face < numFaces; face++) {
                    int id = parcel.readInt();

                    FaceData faceData = new FaceData();

                    int numPoly = parcel.readInt();

                    for (int i = 0; i < numPoly; i++) {
                        Polygon polygon = Polygon.CREATOR.createFromParcel(parcel);
                        PixM contours = PixM.CREATOR.createFromParcel(parcel);
                        int colorFill = parcel.readInt();
                        int colorContours = parcel.readInt();
                        int colorTransparent = parcel.readInt();

                        faceData.getFaceSurfaces().add(new FaceData.Surface(id, polygon, contours, colorFill, colorContours,
                                colorTransparent));

                    }
                    googleFaceDetection.getDataFaces().add(faceData);
                }
                return googleFaceDetection;
            }

            @Override
            public Object[] newArray(int i) {
                return new Object[0];
            }
        });
    }

    public static final Creator<GoogleFaceDetection> CREATOR = new Creator<GoogleFaceDetection>() {
        @Override
        public GoogleFaceDetection createFromParcel(Parcel in) {
            return new GoogleFaceDetection(in);
        }

        @Override
        public GoogleFaceDetection[] newArray(int size) {
            return new GoogleFaceDetection[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(getDataFaces().size());
        for (int face = 0; face < getDataFaces().size(); face++) {
            parcel.writeInt(getDataFaces().size());

            FaceData faceData =getDataFaces().get(face);

            parcel.writeInt(faceData.getFaceSurfaces().size());

            for (int j = 0; j < faceData.getFaceSurfaces().size(); j++) {
                FaceData.Surface surface = faceData.getFaceSurfaces().get(j);
                parcel.writeParcelable(surface.polygon, 1);
                parcel.writeParcelable(surface.contours, 1);
                parcel.writeInt(surface.colorFill);
                parcel.writeInt(surface.colorContours);
                parcel.writeInt(surface.colorTransparent);

            }
        }
    }

    public static class FaceData {
        @ExperimentalCamera2Interop public static class Surface {
            private int colorFill;
            private int colorContours;
            private int colorTransparent;
            private int surfaceId;
            private Polygon polygon;
            private PixM contours;
            private PixM filledContours;
            @Nullable
            public PixM actualDrawing;

            public Surface(int surfaceId, Polygon polygon, PixM contours, int colorFill, int colorContours, int colorTransparent) {
                this.surfaceId = surfaceId;
                this.polygon = polygon;
                this.contours = contours;
                this.colorFill = colorFill;
                this.colorContours = colorContours;
                this.colorTransparent = colorTransparent;
                filledContours = polygon.fillPolygon2D(this, null, contours.getBitmap(),
                        colorTransparent, 0, new PointF(0,0), 1.0);
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

            @Override
            public String toString() {
                return "Surface{" +
                        "colorFill=" + colorFill +
                        ", colorContours=" + colorContours +
                        ", colorTransparent=" + colorTransparent +
                        ", surfaceId=" + surfaceId +
                        ", polygon=" + polygon +
                        ", contours=" + contours +
                        ", actualDrawing=" + actualDrawing +
                        '}';
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

    public FaceData.Surface getSurface(android.graphics.Point pInPicture) {
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
            return surface[0];
        else
            return null;
    }

    public FaceData.Surface getSelectedSurface() {
        return selectedSurface;
    }

    public void setSelectedSurface(FaceData.Surface selectedSurface) {
        this.selectedSurface = selectedSurface;
    }

    public Bitmap getBitmap() {
        return null;
    }
}