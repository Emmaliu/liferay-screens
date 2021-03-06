﻿using CoreGraphics;
using Foundation;
using ObjCRuntime;
using System;

namespace LiferayScreens
{
    // @interface RatingView_default_thumbs : BaseScreenletView <RatingViewModel>
    [BaseType(typeof(BaseScreenletView))]
    interface RatingView_default_thumbs : IRatingViewModel
    {
        // @property (nonatomic) int32_t defaultRatingsGroupCount;
        [Export("defaultRatingsGroupCount")]
        int DefaultRatingsGroupCount { get; set; }

        // @property (nonatomic, strong) RatingEntry * _Nullable ratingEntry;
        [NullAllowed, Export("ratingEntry", ArgumentSemantic.Strong)]
        RatingEntry RatingEntry { get; set; }

        // -(instancetype _Nonnull)initWithFrame:(CGRect)frame __attribute__((objc_designated_initializer));
        [Export("initWithFrame:")]
        [DesignatedInitializer]
        IntPtr Constructor(CGRect frame);
    }
}
