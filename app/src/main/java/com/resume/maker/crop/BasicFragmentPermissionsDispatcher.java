package com.resume.maker.crop;

import java.lang.ref.WeakReference;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.PermissionUtils;


final class BasicFragmentPermissionsDispatcher {
    private static final int REQUEST_CROPIMAGE = 1;
    private static final int REQUEST_PICKIMAGE = 0;
    private static final String[] PERMISSION_PICKIMAGE = {"android.permission.READ_EXTERNAL_STORAGE"};
    private static final String[] PERMISSION_CROPIMAGE = {"android.permission.WRITE_EXTERNAL_STORAGE"};

    private BasicFragmentPermissionsDispatcher() {
    }

    static void pickImageWithCheck(BasicFragment basicFragment) {
        if (PermissionUtils.hasSelfPermissions(basicFragment.getActivity(), PERMISSION_PICKIMAGE)) {
            basicFragment.pickImage();
        } else if (PermissionUtils.shouldShowRequestPermissionRationale(basicFragment, PERMISSION_PICKIMAGE)) {
            basicFragment.showRationaleForPick(new PickImagePermissionRequest(basicFragment));
        } else {
            basicFragment.requestPermissions(PERMISSION_PICKIMAGE, 0);
        }
    }

    static void cropImageWithCheck(BasicFragment basicFragment) {
        if (PermissionUtils.hasSelfPermissions(basicFragment.getActivity(), PERMISSION_CROPIMAGE)) {
            basicFragment.cropImage();
        } else if (PermissionUtils.shouldShowRequestPermissionRationale(basicFragment, PERMISSION_CROPIMAGE)) {
            basicFragment.showRationaleForCrop(new CropImagePermissionRequest(basicFragment));
        } else {
            basicFragment.requestPermissions(PERMISSION_CROPIMAGE, 1);
        }
    }

    static void onRequestPermissionsResult(BasicFragment basicFragment, int i, int[] iArr) {
        if (i != 0) {
            if (i == 1 && PermissionUtils.verifyPermissions(iArr)) {
                basicFragment.cropImage();
            }
        } else if (PermissionUtils.verifyPermissions(iArr)) {
            basicFragment.pickImage();
        }
    }


    private static final class PickImagePermissionRequest implements PermissionRequest {
        private final WeakReference<BasicFragment> weakTarget;

        @Override
        public void cancel() {
        }

        private PickImagePermissionRequest(BasicFragment basicFragment) {
            this.weakTarget = new WeakReference<>(basicFragment);
        }

        @Override
        public void proceed() {
            BasicFragment basicFragment = this.weakTarget.get();
            if (basicFragment != null) {
                basicFragment.requestPermissions(BasicFragmentPermissionsDispatcher.PERMISSION_PICKIMAGE, 0);
            }
        }
    }


    private static final class CropImagePermissionRequest implements PermissionRequest {
        private final WeakReference<BasicFragment> weakTarget;

        @Override
        public void cancel() {
        }

        private CropImagePermissionRequest(BasicFragment basicFragment) {
            this.weakTarget = new WeakReference<>(basicFragment);
        }

        @Override
        public void proceed() {
            BasicFragment basicFragment = this.weakTarget.get();
            if (basicFragment != null) {
                basicFragment.requestPermissions(BasicFragmentPermissionsDispatcher.PERMISSION_CROPIMAGE, 1);
            }
        }
    }
}
